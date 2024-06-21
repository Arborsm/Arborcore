package dev.arbor.gtnn.client.renderer.machine

import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.client.renderer.machine.IPartRenderer
import com.gregtechceu.gtceu.client.renderer.machine.MachineRenderer
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.resources.model.ModelState
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class BlockMachineRenderer(modelLocation: ResourceLocation) : MachineRenderer(modelLocation), IPartRenderer {

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
        this.renderBaseModel(quads, definition, machine, frontFacing, side, rand)
    }
}
