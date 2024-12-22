package dev.arbor.gtnn.api.machine.multiblock

import com.gregtechceu.gtceu.api.capability.IEnergyContainer
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.gui.fancy.IFancyUIProvider
import com.gregtechceu.gtceu.api.gui.fancy.TooltipsPanel
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.content.Content
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine
import com.gregtechceu.gtceu.utils.FormattingUtil
import com.lowdragmc.lowdraglib.gui.modular.ModularUI
import com.lowdragmc.lowdraglib.gui.widget.*
import com.lowdragmc.lowdraglib.side.item.IItemTransfer
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import dev.arbor.gtnn.api.machine.multiblock.part.HighSpeedPipeBlock
import dev.arbor.gtnn.api.machine.multiblock.part.NeutronAcceleratorMachine
import dev.arbor.gtnn.api.machine.multiblock.part.NeutronSensorMachine
import dev.arbor.gtnn.api.recipe.NeutronActivatorCondition
import dev.arbor.gtnn.data.GTNNItems
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps
import net.minecraft.ChatFormatting
import net.minecraft.MethodsReturnNonnullByDefault
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.Ingredient
import javax.annotation.ParametersAreNonnullByDefault
import kotlin.math.pow

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class NeutronActivatorMachine(holder: IMachineBlockEntity, vararg args: Any) : WorkableMultiblockMachine(holder, args),
    IFancyUIMachine, IDisplayUIMachine, IExplosionMachine {
    @Persisted
    private var height = 0

    @Persisted
    @DescSynced
    private var eV = 0

    @Persisted
    private var isWorking = false
    private var neutronEnergySubs = ConditionalSubscriptionHandler(this, this::neutronEnergyUpdate) { isFormed }
    private var moderateSubs = ConditionalSubscriptionHandler(this, this::moderateUpdate) { eV > 0 }
    private var absorptionSubs = ConditionalSubscriptionHandler(this, this::absorptionUpdate) { eV > 0 }
    private var sensorMachines: HashSet<NeutronSensorMachine>? = null
    private var busMachines: HashSet<ItemBusPartMachine>? = null
    private var acceleratorMachines: HashSet<NeutronAcceleratorMachine>? = null

    //////////////////////////////////////
    //***    Multiblock LifeCycle    ***//
    //////////////////////////////////////
    override fun onStructureFormed() {
        // Declare 'height' as a local variable if not used elsewhere
        height = 0
        super.onStructureFormed()

        // Cache the Map access to avoid repeated calls
        val matchContext = getMultiblockState().matchContext
        val ioMap = matchContext.getOrCreate<Long2ObjectMap<Any>>("ioMap", Long2ObjectMaps::emptyMap)

        // Cache the result of getParts() to prevent repetitive calls
        val parts = getParts()
        for (part in parts) {
            val io = ioMap.getOrDefault(part.self().pos.asLong(), IO.BOTH)
            if (io == IO.NONE) continue

            for (handler in part.recipeHandlers) {
                val handlerIO = handler.handlerIO
                if (io != IO.BOTH && handlerIO != IO.BOTH && io != handlerIO) continue
                if (handler.capability == EURecipeCapability.CAP && handler is IEnergyContainer) {
                    traitSubscriptions.add(handler.addChangedListener(neutronEnergySubs::updateSubscription))
                    traitSubscriptions.add(handler.addChangedListener(moderateSubs::updateSubscription))
                }
                if (handler.capability == ItemRecipeCapability.CAP && handler is IItemTransfer) {
                    if (handlerIO == IO.IN || handlerIO == IO.BOTH) {
                        traitSubscriptions.add(handler.addChangedListener(absorptionSubs::updateSubscription))
                    }
                }
            }
            if (part is ItemBusPartMachine) {
                busMachines = APartAbility.getOrDefault(busMachines, ::hashSetOf)
                busMachines!!.add(part)
            }
            if (part is NeutronSensorMachine) {
                sensorMachines = APartAbility.getOrDefault(sensorMachines, ::hashSetOf)
                sensorMachines!!.add(part)
            }
            if (part is NeutronAcceleratorMachine) {
                acceleratorMachines = APartAbility.getOrDefault(acceleratorMachines, ::hashSetOf)
                acceleratorMachines!!.add(part)
            }
            if (part is HighSpeedPipeBlock) height++
        }

        neutronEnergySubs.initialize(level)
    }

    override fun onLoad() {
        super.onLoad()
        moderateSubs.initialize(level)
    }

    override fun onStructureInvalid() {
        super.onStructureInvalid()
        height = 0
        sensorMachines = null
        busMachines = null
        acceleratorMachines = null
    }

    //////////////////////////////////////
    //***  Multiblock Subscriptions  ***//
    //////////////////////////////////////

    private fun neutronEnergyUpdate() {
        if (acceleratorMachines == null) return

        var anyWorking = false
        for (accelerator in acceleratorMachines!!) {
            val increase = accelerator.consumeEnergy()
            if (increase > 0) {
                anyWorking = true
                this.eV += (Math.round((increase * getEfficiencyFactor()).coerceAtLeast(1.0))).toInt()
            }
        }

        this.isWorking = anyWorking

        if (this.eV > MAX_ENERGY) doExplosion(4F * 32F)

        if (!isWorking) neutronEnergySubs.unsubscribe()
    }

    private fun moderateUpdate() {
        if (!isWorking && offsetTimer % 20 == 0L) {
            this.eV = (eV - 72 * K).coerceAtLeast(0)
        }
        if (this.eV < 0) this.eV = 0

        if (!isFormed() || sensorMachines == null) return
        sensorMachines!!.forEach { sensor -> sensor.update(eV) }
    }

    private fun absorptionUpdate() {
        if (busMachines == null || eV <= 0) return

        var hasSlower = false
        for (bus in busMachines!!) {
            val inv = bus.inventory
            val io = inv.getHandlerIO()
            if (io == IO.IN || io == IO.BOTH) {
                for (i in 0 until inv.slots) {
                    val dustBeryllium = ChemicalHelper.get(TagPrefix.dust, GTMaterials.Beryllium).item
                    val dustGraphite = ChemicalHelper.get(TagPrefix.dust, GTMaterials.Graphite).item
                    val stack = inv.getStackInSlot(i)
                    if (stack.`is`(dustBeryllium) || stack.`is`(dustGraphite)) {
                        hasSlower = true
                        val consume = (eV / (10 * M)).coerceAtLeast(1).coerceAtMost(stack.count)
                        inv.extractItemInternal(i, consume, false)
                        this.eV -= 10 * M * consume
                    }
                }
            }
        }

        if (!hasSlower) absorptionSubs.unsubscribe()
    }

    //////////////////////////////////////
    //**********     GUI     ***********//
    //////////////////////////////////////
    override fun addDisplayText(textList: MutableList<Component>) {
        super.addDisplayText(textList)
        if (isFormed()) {
            textList.add(
                Component.translatable(recipeType.registryName.toLanguageKey()).setStyle(
                    Style.EMPTY.withColor(ChatFormatting.AQUA).withHoverEvent(
                        HoverEvent(
                            HoverEvent.Action.SHOW_TEXT, Component.translatable("gtceu.gui.machinemode.title")
                        )
                    )
                )
            )
            if (!isWorkingEnabled) {
                textList.add(Component.translatable("gtceu.multiblock.work_paused"))
            } else if (isActive) {
                textList.add(Component.translatable("gtceu.multiblock.running"))
                val currentProgress = (recipeLogic.progressPercent * 100).toInt()
                textList.add(Component.translatable("gtceu.multiblock.progress", currentProgress))
            } else {
                textList.add(Component.translatable("gtceu.multiblock.idling"))
            }
            if (recipeLogic.isWaiting) {
                textList.add(
                    Component.translatable("gtceu.multiblock.waiting")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
                )
            }
            textList.add(Component.translatable("gtnn.multiblock.neutronactivator.ev", processNumber(eV)))
            textList.add(
                Component.translatable(
                    "gtnn.multiblock.neutronactivator.height", FormattingUtil.formatNumbers(height)
                )
            )
            textList.add(
                Component.translatable(
                    "gtnn.multiblock.neutronactivator.efficiency",
                    FormattingUtil.formatNumbers(getEfficiencyFactor() * 100)
                )
            )
        }
        definition.additionalDisplay.accept(this, textList)
    }

    private fun processNumber(num: Int): String {
        var num2 = num.toFloat() / 1000F
        if (num2 <= 0) {
            return String.format("%d", num)
        }
        if (num2 < 1000.0) {
            return String.format("%.1fK", num2)
        }
        num2 /= 1000F
        return String.format("%.1fM", num2)
    }

    override fun createUIWidget(): Widget {
        val group = WidgetGroup(0, 0, 170 + 8, 129 + 8)
        val container = WidgetGroup(4, 4, 170, 129)
        container.addWidget(
            DraggableScrollableWidgetGroup(4, 4, 162, 121).setBackground(screenTexture)
                .addWidget(LabelWidget(4, 5, self().blockState.block.descriptionId)).addWidget(
                    ComponentPanelWidget(4, 17, this::addDisplayText).setMaxWidthLimit(150)
                        .clickHandler(this::handleDisplayClick)
                )
        )
        container.setBackground(GuiTextures.BACKGROUND_INVERSE)
        group.addWidget(container)
        return group
    }

    override fun createUI(entityPlayer: Player): ModularUI {
        return super<IFancyUIMachine>.createUI(entityPlayer)
    }

    override fun isRemote(): Boolean {
        return super<IFancyUIMachine>.isRemote()
    }

    override fun getSubTabs(): List<IFancyUIProvider> {
        return getParts().filter { e -> e !is HighSpeedPipeBlock }
            .filter(IFancyUIProvider::class.java::isInstance)
            .map(IFancyUIProvider::class.java::cast)
    }

    override fun attachTooltips(tooltipsPanel: TooltipsPanel) {
        for (part in getParts()) {
            part.attachFancyTooltipsToController(this, tooltipsPanel)
        }
    }

    //////////////////////////////////////
    //******   Multiblock Data   *******//
    //////////////////////////////////////
    private fun getVelocityFactor(): Double {
        return 0.9.pow((height - 4).coerceAtLeast(0).toDouble())
    }

    private fun getEfficiencyFactor(): Double {
        return 0.95.pow((height - 4).coerceAtLeast(0).toDouble())
    }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    //////////////////////////////////////
    //******     RECIPE LOGIC    *******//
    //////////////////////////////////////

    override fun alwaysTryModifyRecipe(): Boolean {
        return true
    }

    override fun getRealRecipe(recipe: GTRecipe): GTRecipe? {
        val conditions = recipe.conditions.filter(NeutronActivatorCondition::class.java::isInstance)
        val newRecipe = recipe.copy()
        newRecipe.duration = Math.round((newRecipe.duration * getVelocityFactor()).coerceAtLeast(1.0)).toInt()
        if (conditions.isNotEmpty()) {
            val condition = conditions[0] as NeutronActivatorCondition
            if (eV > (condition.evRange / 10000) * 1000000 || eV < (condition.evRange % 10000) * 1000000) {
                newRecipe.outputs.clear()
                newRecipe.outputs[ItemRecipeCapability.CAP] =
                    listOf(Content(Ingredient.of(GTNNItems.RADIOACTIVE_WASTE), 1, 1, 0, "", ""))
            }
        }
        return super.getRealRecipe(newRecipe)
    }


    companion object {
        val MANAGED_FIELD_HOLDER =
            ManagedFieldHolder(NeutronActivatorMachine::class.java, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER)
        private const val M = 1000000
        private const val K = 1000
        private const val MAX_ENERGY = 1200 * M

        fun checkNeutronActivatorCondition(machine: MetaMachine, recipe: GTRecipe): Boolean {
            if (machine is NeutronActivatorMachine) {
                return recipe.conditions[0] is NeutronActivatorCondition
            }
            return false
        }
    }
}
