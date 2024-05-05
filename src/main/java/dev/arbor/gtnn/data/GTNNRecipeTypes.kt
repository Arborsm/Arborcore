package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.api.recipe.ui.GTRecipeTypeUI
import com.gregtechceu.gtceu.common.data.GTRecipeTypes
import com.gregtechceu.gtceu.common.data.GTSoundEntries
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection
import dev.arbor.gtnn.api.tool.StringTools.nn

object GTNNRecipeTypes {
    val CHEMICAL_PLANT_RECIPES: GTRecipeType =
        GTRecipeTypes.register("chemical_plant", GTRecipeTypes.MULTIBLOCK).setMaxIOSize(4, 4, 4, 4).setEUIO(IO.IN)
            .prepareBuilder { gtRecipeBuilder: GTRecipeBuilder ->
                gtRecipeBuilder.EUt(
                    GTValues.VA[GTValues.LV].toLong()
                )
            }.setSlotOverlay(false, false, GuiTextures.BOX_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, FillDirection.LEFT_TO_RIGHT).setMaxTooltips(4)
            .setSound(GTSoundEntries.COOLING)

    val NEUTRON_ACTIVATOR_RECIPES: GTRecipeType =
        GTRecipeTypes.register("neutron_activator", GTRecipeTypes.MULTIBLOCK).setMaxIOSize(9, 9, 1, 1).setMaxTooltips(5)
            .setSound(GTSoundEntries.COOLING)

    val DEHYDRATOR_RECIPES: GTRecipeType =
        GTRecipeTypes.register("dehydrator", GTRecipeTypes.ELECTRIC).setMaxIOSize(2, 9, 1, 1).setEUIO(
            IO.IN
        ).prepareBuilder { recipeBuilder: GTRecipeBuilder -> recipeBuilder.EUt(5) }
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CENTRIFUGE)

    val NAQUADAH_REACTOR_RECIPES: GTRecipeType =
        GTRecipeTypes.register("naquadah_reactor", GTRecipeTypes.ELECTRIC).setMaxIOSize(1, 1, 0, 0).setEUIO(IO.OUT)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CENTRIFUGE)

    val STONE_BEDROCK_ORE_MACHINE_RECIPES: GTRecipeType =
        GTRecipeTypes.register("homemade_bedrock_ore_machine", GTRecipeTypes.STEAM)
            .setMaxIOSize(1, 6, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, FillDirection.LEFT_TO_RIGHT)
            .setRecipeUI(
                GTRecipeTypeUI(
                    GTRecipeType("dummy".nn(), "dummy").setMaxIOSize(0, 81, 0, 0)
                )
            )
            .setSound(GTSoundEntries.FURNACE)

    val ROCKET_ENGINE_RECIPES: GTRecipeType =
        GTRecipeTypes.register("rocket_engine", GTRecipeTypes.ELECTRIC).setMaxIOSize(0, 0, 1, 1).setEUIO(IO.OUT)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CENTRIFUGE)

    val LARGE_NAQUADAH_REACTOR_RECIPES: GTRecipeType =
        GTRecipeTypes.register("large_naquadah_reactor", GTRecipeTypes.MULTIBLOCK).setMaxIOSize(0, 0, 1, 1)
            .setEUIO(IO.OUT).setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CENTRIFUGE)

    val PRECISION_ASSEMBLY_RECIPES: GTRecipeType =
        GTRecipeTypes.register("precision_assembly", GTRecipeTypes.MULTIBLOCK).setMaxIOSize(4, 1, 4, 0).setEUIO(IO.IN)
            .prepareBuilder { gtRecipeBuilder: GTRecipeBuilder ->
                gtRecipeBuilder.EUt(
                    GTValues.VA[GTValues.LV].toLong()
                )
            }.setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING)

    fun init() {
    }
}
