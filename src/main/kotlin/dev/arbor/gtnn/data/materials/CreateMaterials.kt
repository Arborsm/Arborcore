package dev.arbor.gtnn.data.materials

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.material.material.info.MaterialFlags
import com.gregtechceu.gtceu.api.material.material.info.MaterialIconSet
import com.gregtechceu.gtceu.data.material.GTMaterials
import dev.arbor.gtnn.GTNNIntegration.isGreateLoaded
import dev.arbor.gtnn.data.GTNNMaterials.*

object CreateMaterials {
    fun init() {
        if (isGreateLoaded()) {
            AndesiteAlloy = GTCEuAPI.materialManager.getMaterial("andesite_alloy")
            RefinedRadiance = GTCEuAPI.materialManager.getMaterial("refined_radiance")
            ShadowSteel = GTCEuAPI.materialManager.getMaterial("shadow_steel")
        } else {
            AndesiteAlloy =
                Builder("andesite_alloy").ingot().fluid().color(0x99B09F).iconSet(MaterialIconSet.DULL)
                    .buildAndRegister().setFormula("(Mg3Si2H4O9)4(KNO3)Fe")

            RefinedRadiance = Builder("refined_radiance").ingot().fluid().color(0xfffef9)
                .iconSet(MaterialIconSet.METALLIC).appendFlags(
                    GTMaterials.EXT2_METAL,
                    MaterialFlags.GENERATE_FINE_WIRE,
                    MaterialFlags.GENERATE_GEAR,
                    MaterialFlags.GENERATE_FRAME
                ).buildAndRegister()

            ShadowSteel =
                Builder("shadow_steel").ingot().fluid().color(0x35333c).iconSet(MaterialIconSet.METALLIC)
                    .appendFlags(
                        GTMaterials.EXT2_METAL,
                        MaterialFlags.GENERATE_FINE_WIRE,
                        MaterialFlags.GENERATE_GEAR,
                        MaterialFlags.GENERATE_FRAME
                    ).buildAndRegister()
        }
    }
}
