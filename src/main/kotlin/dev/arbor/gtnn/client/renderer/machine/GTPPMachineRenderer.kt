package dev.arbor.gtnn.client.renderer.machine

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.capability.IWorkable
import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine
import com.gregtechceu.gtceu.client.model.WorkableOverlayModel
import com.gregtechceu.gtceu.client.renderer.machine.IControllerRenderer
import com.gregtechceu.gtceu.client.renderer.machine.MachineRenderer
import com.lowdragmc.lowdraglib.client.bakedpipeline.FaceQuad
import com.lowdragmc.lowdraglib.client.model.ModelFactory
import com.tterrag.registrate.util.entry.BlockEntry
import dev.arbor.gtnn.api.machine.feature.IGTPPMachine
import dev.arbor.gtnn.block.PlantCasingBlock
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.resources.model.ModelState
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.block.Block
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import java.util.function.Consumer
import java.util.function.Supplier


@OnlyIn(Dist.CLIENT)
class GTPPMachineRenderer(baseCasing: ResourceLocation, workableModel: ResourceLocation, tint: Boolean) :
    MachineRenderer(if (tint) GTCEu.id("block/tinted_cube_all") else GTCEu.id("block/cube_all")), IControllerRenderer {
    private val overlayModel = WorkableOverlayModel(workableModel)

    init {
        setTextureOverride(mapOf("all" to baseCasing))
    }

    @OnlyIn(Dist.CLIENT)
    private fun render(
        side: Direction?,
        modelFacing: Direction?,
        quads: MutableList<BakedQuad>,
        machine: MetaMachine,
        modelState: ModelState
    ) {
        var casing: BlockEntry<Block>? = null
        if (side != null && modelFacing != null && machine is IGTPPMachine) {
            quads.add(
                FaceQuad.bakeFace(
                    modelFacing,
                    ModelFactory.getBlockSprite(PlantCasingBlock.getByTier(machine.getTier()).getResourceLocation()),
                    modelState
                )
            )
            casing = PlantCasingBlock.getByTier(machine.getTier()).getPlantCasing(machine.getTier())
        }
        machine.self().definition.appearance = Supplier { casing?.defaultState }
    }

    @OnlyIn(Dist.CLIENT)
    override fun renderMachine(
        quads: MutableList<BakedQuad>?,
        definition: MachineDefinition?,
        machine: MetaMachine?,
        frontFacing: Direction?,
        side: Direction?,
        rand: RandomSource?,
        modelFacing: Direction?,
        modelState: ModelState?
    ) {
        super.renderMachine(quads, definition, machine, frontFacing, side, rand, modelFacing, modelState)
        if (machine is IGTPPMachine && machine is MultiblockControllerMachine) {
            if (machine.isFormed) {
                quads!!.clear()
                render(side, modelFacing, quads, machine, modelState!!)
            }
        }

        if (machine is IWorkable) {
            quads!!.addAll(
                overlayModel.bakeQuads(
                    side,
                    frontFacing!!,
                    Direction.NORTH,
                    machine.isActive,
                    machine.isWorkingEnabled
                )
            )
        } else {
            quads!!.addAll(overlayModel.bakeQuads(side, frontFacing!!, Direction.NORTH, false, false))
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun renderPartModel(
        quads: MutableList<BakedQuad>?,
        machine: IMultiController?,
        part: IMultiPart?,
        frontFacing: Direction?,
        side: Direction?,
        rand: RandomSource?,
        modelFacing: Direction?,
        modelState: ModelState?
    ) {
        render(side, modelFacing, quads!!, machine!!.self(), modelState!!)
    }

    @OnlyIn(Dist.CLIENT)
    override fun onPrepareTextureAtlas(atlasName: ResourceLocation, register: Consumer<ResourceLocation>) {
        super.onPrepareTextureAtlas(atlasName, register)
        if (atlasName == InventoryMenu.BLOCK_ATLAS) {
            this.overlayModel.registerTextureAtlas(register)
        }
    }
}
