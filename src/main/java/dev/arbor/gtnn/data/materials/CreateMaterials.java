package dev.arbor.gtnn.data.materials;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import dev.arbor.gtnn.GTNNIntegration;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.DULL;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.METALLIC;
import static com.gregtechceu.gtceu.common.data.GTMaterials.EXT2_METAL;
import static dev.arbor.gtnn.data.GTNNMaterials.*;

public class CreateMaterials {
    public static void init(){
        if (GTNNIntegration.INSTANCE.isGreateLoaded()) {
            AndesiteAlloy = GTCEuAPI.materialManager.getMaterial("andesite_alloy");
            RefinedRadiance = GTCEuAPI.materialManager.getMaterial("refined_radiance");
            ShadowSteel = GTCEuAPI.materialManager.getMaterial("shadow_steel");
        } else {
            AndesiteAlloy = Builder("andesite_alloy")
                    .ingot().fluid()
                    .color(0x99B09F).iconSet(DULL)
                    .buildAndRegister()
                    .setFormula("(Mg3Si2H4O9)4(KNO3)Fe");
            RefinedRadiance = Builder("refined_radiance")
                    .ingot().fluid()
                    .color(0xfffef9).iconSet(METALLIC)
                    .appendFlags(EXT2_METAL, GENERATE_FINE_WIRE, GENERATE_GEAR, GENERATE_FRAME)
                    .buildAndRegister();
            ShadowSteel = Builder("shadow_steel")
                    .ingot().fluid()
                    .color(0x35333c).iconSet(METALLIC)
                    .appendFlags(EXT2_METAL, GENERATE_FINE_WIRE, GENERATE_GEAR, GENERATE_FRAME)
                    .buildAndRegister();
        }
    }
}
