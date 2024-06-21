package dev.arbor.gtnn.api.machine.multiblock.part

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.gui.widget.ToggleButtonWidget
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredPartMachine
import com.gregtechceu.gtceu.utils.RedstoneUtil
import com.lowdragmc.lowdraglib.gui.editor.ColorPattern
import com.lowdragmc.lowdraglib.gui.widget.*
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import com.lowdragmc.lowdraglib.utils.LocalizationUtils
import com.lowdragmc.lowdraglib.utils.Position
import com.lowdragmc.lowdraglib.utils.Size
import net.minecraft.MethodsReturnNonnullByDefault
import net.minecraft.core.Direction
import javax.annotation.ParametersAreNonnullByDefault

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class NeutronSensorMachine(holder: IMachineBlockEntity) : TieredPartMachine(holder, GTValues.IV) {
    @Persisted
    private var energy = 0

    @Persisted
    @DescSynced
    private var min = 0
        private set(min) {
            field = min
            update()
        }

    @Persisted
    @DescSynced
    private var max = 0
        private set(max) {
            field = max
            update()
        }

    @Persisted
    private var isInverted = false
        private set(isInverted) {
            field = isInverted
            update()
        }

    @Persisted
    private var redstoneSignalOutput = 0
        private set(redstoneSignalOutput) {
            field = redstoneSignalOutput
            updateSignal()
        }

    private val k = 1000

    private fun clamp(value: Int, min: Int, max: Int): Int {
        return if (value < min) {
            min
        } else {
            value.coerceAtMost(max)
        }
    }

    //////////////////////////////////////
    //**********     GUI     ***********//
    //////////////////////////////////////
    override fun createUIWidget(): Widget {
        val group = WidgetGroup(Position.ORIGIN, Size(176, 112))

        group.addWidget(
            TextBoxWidget(
                8, 35, 65, listOf(LocalizationUtils.format("gtnn.universal.desc.neutron_kinetic_energy.min", "KeV"))
            )
        )

        group.addWidget(
            TextBoxWidget(
                8, 80, 65, listOf(LocalizationUtils.format("gtnn.universal.desc.neutron_kinetic_energy.max", "KeV"))
            )
        )

        group.addWidget(object :
            TextFieldWidget(80, 35, 85, 18, { toText(min) }, { min = clamp(fromText(it), 0, max) }) {
            var maxValue = 0

            override fun updateScreen() {
                super.updateScreen()
                if (maxValue != max) {
                    maxValue = max
                    setNumbersOnly(0, maxValue)
                }
            }
        }.setNumbersOnly(0, max))

        group.addWidget(object :
            TextFieldWidget(80, 80, 85, 18, { toText(max) }, { max = clamp(fromText(it), min, 1200000) }) {
            var minValue = 0

            override fun updateScreen() {
                super.updateScreen()
                if (minValue != min) {
                    minValue = min
                    setNumbersOnly(minValue, 1200000)
                }
            }
        }.setNumbersOnly(min, 1200000))

        group.addWidget(
            ToggleButtonWidget(
                8, 8, 20, 20, GuiTextures.INVERT_REDSTONE_BUTTON, this::isInverted
            ) { this.isInverted = it }.setTooltipText("gui.gtnn.neutron_sensor.invert")
        )
        group.addWidget(
            LabelWidget(80, 13, "1000 KeV = 1 MeV").setTextColor(ColorPattern.BLACK.color).setDropShadow(false)
        )
        return group
    }

    //////////////////////////////////////
    //********     Redstone     ********//
    //////////////////////////////////////

    fun update(energy: Int) {
        this.energy = energy
        val output = RedstoneUtil.computeRedstoneBetweenValues(
            energy.toLong(), (max * k).toFloat(), (min * k).toFloat(), isInverted
        )
        if (redstoneSignalOutput != output) {
            redstoneSignalOutput = output
        }
    }

    private fun update() {
        update(energy)
        updateSignal()
    }

    override fun getOutputSignal(side: Direction?): Int {
        if (side == getFrontFacing().opposite) {
            return redstoneSignalOutput
        }
        return 0
    }

    override fun canConnectRedstone(side: Direction?): Boolean {
        return false
    }

    //////////////////////////////////////
    //**********     Data     **********//
    //////////////////////////////////////
    private fun toText(num: Int): String {
        return num.toString()
    }

    private fun fromText(num: String): Int {
        return Integer.parseInt(num)
    }

    override fun getFieldHolder(): ManagedFieldHolder {
        return MANAGED_FIELD_HOLDER
    }

    companion object {
        private val MANAGED_FIELD_HOLDER = ManagedFieldHolder(
            NeutronSensorMachine::class.java, TieredPartMachine.MANAGED_FIELD_HOLDER
        )
    }
}
