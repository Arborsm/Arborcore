package dev.arbor.gtnn.data

import com.gregtechceu.gtceu.api.worldgen.GTLayerPattern
import com.gregtechceu.gtceu.api.worldgen.GTOreDefinition
import com.gregtechceu.gtceu.api.worldgen.WorldGenLayers
import com.gregtechceu.gtceu.api.worldgen.generator.indicators.SurfaceIndicatorGenerator
import com.gregtechceu.gtceu.api.worldgen.generator.veins.LayeredVeinGenerator
import com.gregtechceu.gtceu.data.block.GTOres
import com.gregtechceu.gtceu.data.material.GTMaterials
import dev.arbor.gtnn.GTNN.id
import dev.arbor.gtnn.GTNNIntegration.isAdAstraLoaded
import dev.arbor.gtnn.GTNNIntegration.isTwilightForestLoaded
import dev.arbor.gtnn.data.GTNNWorld.GTNNWorldGenLayers
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.dimension.BuiltinDimensionTypes
import java.util.function.Consumer

@Suppress("UNUSED")
object GTNNOres {
    val kaolinite_vein: GTOreDefinition = create(
        "kaolinite_vein", { vein: GTOreDefinition ->
            vein.clusterSize(24).weight(60).layer(WorldGenLayers.STONE).density(0.4f)
                .dimensions(BuiltinDimensionTypes.OVERWORLD.location()).heightRangeUniform(30, 70)
                .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTNNMaterials.Kaolinite).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Zeolite).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.FullersEarth).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.GlauconiteSand).size(1, 1)
                                }
                        }
                }
        }, true
    )
    val WOLLASTONITE_VEIN: GTOreDefinition = create(
        "wollastonite_vein", { vein: GTOreDefinition ->
            vein.clusterSize(36).weight(40).layer(WorldGenLayers.STONE).density(0.4f)
                .dimensions(BuiltinDimensionTypes.OVERWORLD.location()).heightRangeUniform(120, 200)
                .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTNNMaterials.Dolomite).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTNNMaterials.Wollastonite).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Trona).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Andradite).size(1, 1)
                                }
                        }
                }
        }, true
    )
    val GALENA_VEIN_TF: GTOreDefinition = create(
        "galena_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(30).weight(40).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Galena).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Silver).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Lead).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Galena)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )
    val SAPPHIRE_VEIN_TF: GTOreDefinition = create(
        "sapphire_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(25).weight(60).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Almandine).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Pyrope).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Sapphire).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.GreenSapphire).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Sapphire)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 橄榄石
    val OLIVINE_VEIN_TF: GTOreDefinition = create(
        "olivine_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(30).weight(20).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Bentonite).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Magnetite).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Olivine).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.GlauconiteSand).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Olivine)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 镍
    val NICKEL_VEIN_TF: GTOreDefinition = create(
        "nickel_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(30).weight(40).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Garnierite).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Nickel).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Cobaltite).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Pentlandite).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Nickel)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 钻石
    val DIAMOND_VEIN_TF: GTOreDefinition = create(
        "diamond_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(30).weight(40).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Graphite).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Diamond).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Coal).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Diamond)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 青金石
    val LAPIS_VEIN_TF: GTOreDefinition = create(
        "lapis_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(40).weight(40).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Lazurite).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Sodalite).size(1, 2)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Lapis).size(1, 2)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Calcite).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Lapis)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 钼
    val MOLYBDENITE_VEIN_TF: GTOreDefinition = create(
        "molybdenite_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(25).weight(5).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Wulfenite).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Molybdenite).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Molybdenum).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Powellite).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Molybdenite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 煤
    val COAL_VEIN_TF: GTOreDefinition = create(
        "coal_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(35).weight(80).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Coal).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Coal).size(2, 4)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Coal)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 皂石
    val LUBRICANT_VEIN_TF: GTOreDefinition = create(
        "lubricant_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(25).weight(40).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Soapstone).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Talc).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.GlauconiteSand).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Pentlandite).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Soapstone)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 金
    val GOLD_VEIN_TF: GTOreDefinition = create(
        "gold_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(35).weight(80).layer(GTNNWorldGenLayers.TF).density(0.15f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Magnetite).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.VanadiumMagnetite).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Gold).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Gold)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 铁
    val IRON_VEIN_TF: GTOreDefinition = create(
        "iron_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(36).weight(120).layer(GTNNWorldGenLayers.TF).density(0.3f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        5
                                    ).mat(GTMaterials.Goethite).size(1, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.YellowLimonite).size(1, 2)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Hematite).size(1, 2)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Malachite).size(1, 2)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Goethite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 磷灰石
    val APATITE_VEIN_TF: GTOreDefinition = create(
        "apatite_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(30).weight(40).layer(GTNNWorldGenLayers.TF).density(0.25f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Apatite).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.TricalciumPhosphate).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Pyrochlore).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Apatite)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 盐
    val SALTS_VEIN_TF: GTOreDefinition = create(
        "salts_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(30).weight(50).layer(GTNNWorldGenLayers.TF).density(0.2f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.RockSalt).size(2, 4)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        2
                                    ).mat(GTMaterials.Salt).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Lepidolite).size(1, 1)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Spodumene).size(1, 1)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Salt)
                        .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE).density(0.4f).radius(5)
                }
        }, true
    )

    // 锡
    val CASSITERITE_VEIN_TF: GTOreDefinition = create(
        "cassiterite_vein_tf", { vein: GTOreDefinition ->
            vein.clusterSize(36).weight(50).layer(GTNNWorldGenLayers.TF).density(0.4f).dimensions(twilightForest())
                .heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
                .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                    generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                            pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        3
                                    ).mat(GTMaterials.Tin).size(2, 3)
                                }.layer { l: GTLayerPattern.Layer.Builder ->
                                    l.weight(
                                        1
                                    ).mat(GTMaterials.Cassiterite).size(1, 2)
                                }
                        }
                }.surfaceIndicatorGenerator { indicator: SurfaceIndicatorGenerator ->
                    indicator.surfaceRock(GTMaterials.Tin).placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                        .density(0.4f).radius(5)
                }
        }, true
    )

    // moon
    // 独居石
    val MONAZITE_VEIN_N: GTOreDefinition = create(
        "monazite_vein_n"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(30).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MOON, GTNNWorld.VENUS, GTNNWorld.GLACIO).heightRangeUniform(20, 40)
            .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Bastnasite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Monazite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Neodymium).size(1, 1)
                            }
                    }
            }
    }

    // 铝土
    val BAUXITE_VEIN: GTOreDefinition = create(
        "bauxite_vein"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(80).layer(GTNNWorldGenLayers.AD).density(0.3f).dimensions(GTNNWorld.MOON)
            .heightRangeUniform(10, 80).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Bauxite).size(1, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Ilmenite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Aluminium).size(1, 1)
                            }
                    }
            }
    }

    // 钛铁
    val ILMENITE_VEIN: GTOreDefinition = create(
        "ilmenite_vein"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(16).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.MOON)
            .heightRangeUniform(-70, 10).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Ilmenite).size(1, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Chromite).size(1, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Uvarovite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Perlite).size(1, 1)
                            }
                    }
            }
    }

    // 石英
    val QUARTZITE_VEIN: GTOreDefinition = create(
        "quartzite_vein"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(20).layer(GTNNWorldGenLayers.AD).density(0.3f)
            .dimensions(GTNNWorld.MOON, GTNNWorld.MARS, GTNNWorld.VENUS).heightRangeUniform(30, 80)
            .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Quartzite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Barite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.CertusQuartz).size(2, 4)
                            }
                    }
            }
    }

    // 钼
    val MOLYBDENUM_VEIN_AD: GTOreDefinition = create(
        "molybdenum_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(25).weight(5).layer(GTNNWorldGenLayers.AD).density(0.25f)
            .dimensions(GTNNWorld.MOON, GTNNWorld.MERCURY).heightRangeUniform(20, 50).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Wulfenite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Molybdenite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Molybdenum).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Powellite).size(1, 1)
                            }
                    }
            }
    }

    // 方铅矿
    val GALENA_VEIN_AD: GTOreDefinition = create(
        "galena_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(30).weight(40).layer(GTNNWorldGenLayers.AD).density(0.25f)
            .dimensions(GTNNWorld.MOON, GTNNWorld.MARS, GTNNWorld.VENUS, GTNNWorld.GLACIO).heightRangeUniform(-15, 45)
            .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Galena).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Silver).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Lead).size(1, 1)
                            }
                    }
            }
    }

    // 铜
    val COPPER_VEIN_AD: GTOreDefinition = create(
        "copper_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(80).layer(GTNNWorldGenLayers.AD).density(0.3f)
            .dimensions(GTNNWorld.MOON, GTNNWorld.MERCURY).heightRangeUniform(-40, 15).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Chalcopyrite).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Iron).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Pyrite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Copper).size(1, 2)
                            }
                    }
            }
    }

    // 锡石
    val CASSITERITE_VEIN_AD: GTOreDefinition = create(
        "cassiterite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(50).layer(GTNNWorldGenLayers.AD).density(0.4f)
            .dimensions(GTNNWorld.MOON, GTNNWorld.VENUS).heightRangeUniform(10, 80).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Tin).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Cassiterite).size(1, 2)
                            }
                    }
            }
    }

    // 戴斯
    val DESH_VEIN_AD: GTOreDefinition = create(
        "desh_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(30).layer(GTNNWorldGenLayers.AD).density(0.3f).dimensions(GTNNWorld.MOON)
            .heightRangeUniform(5, 40).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTNNMaterials.Desh).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTNNMaterials.ArcaneCrystal).size(1, 2)
                            }
                    } //.layer(l -> l.weight(1).state(() -> Block.getBlock("forbidden_arcanus:xpetrified_ore").defaultBlockState()))
            }
    }

    // Mars
    // 紫金
    val OSTRUM_VEIN_AD: GTOreDefinition = create(
        "ostrum_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(30).layer(GTNNWorldGenLayers.AD).density(0.3f).dimensions(GTNNWorld.MARS)
            .heightRangeUniform(5, 40).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTNNMaterials.Ostrum).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Scheelite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Tungstate).size(1, 1)
                            }
                    }
            }
    }

    // 坤
    val ARSENIC_VEIN_AD: GTOreDefinition = create(
        "arsenic_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(32).weight(60).layer(GTNNWorldGenLayers.AD).density(0.4f).dimensions(GTNNWorld.MARS)
            .heightRangeUniform(40, 60).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Arsenic).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Bismuth).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Antimony).size(1, 2)
                            }
                    }
            }
    }

    // 沥青铀
    val PITCHBLENDE_VEIN_AD: GTOreDefinition = create(
        "pitchblende_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(40).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.VENUS).heightRangeUniform(20, 60).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Pitchblende).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Uraninite).size(1, 2)
                            }
                    }
            }
    }

    // 晶质铀
    val TUFF_URANINITE_VEIN_AD: GTOreDefinition = create(
        "tuff_uraninite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(20).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.MERCURY).heightRangeUniform(20, 30).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Uraninite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Uranium238).size(1, 2)
                            }
                    }
            }
    }

    // 钨酸锂
    val SCHEELITE_VEIN_AD: GTOreDefinition = create(
        "scheelite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(16).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.GLACIO).heightRangeUniform(20, 60).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Scheelite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Tungstate).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Lithium).size(1, 1)
                            }
                    }
            }
    }

    // 硫
    val SULFUR_VEIN_AD: GTOreDefinition = create(
        "sulfur_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(30).weight(100).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.VENUS).heightRangeUniform(10, 30).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Sulfur).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Pyrite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Sphalerite).size(1, 1)
                            }
                    }
            }
    }

    // 红石
    val REDSTONE_VEIN_AD: GTOreDefinition = create(
        "redstone_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(30).weight(60).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.VENUS).heightRangeUniform(5, 40).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Redstone).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Ruby).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Cinnabar).size(1, 1)
                            }
                    }
            }
    }

    // 镍
    val NICKEL_VEIN_AD: GTOreDefinition = create(
        "nickel_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(30).weight(40).layer(GTNNWorldGenLayers.AD).density(0.25f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.VENUS, GTNNWorld.GLACIO).heightRangeUniform(-10, 60)
            .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Garnierite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Nickel).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Cobaltite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Pentlandite).size(1, 1)
                            }
                    }
            }
    }

    // 金
    val MAGNETITE_VEIN_AD: GTOreDefinition = create(
        "magnetite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(35).weight(80).layer(GTNNWorldGenLayers.AD).density(0.15f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.MERCURY, GTNNWorld.GLACIO).heightRangeUniform(10, 60)
            .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Magnetite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.VanadiumMagnetite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Gold).size(1, 1)
                            }
                    }
            }
    }

    // 铁
    val IRON_VEIN_AD: GTOreDefinition = create(
        "iron_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(120).layer(GTNNWorldGenLayers.AD).density(0.3f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.MERCURY).heightRangeUniform(-10, 60).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    5
                                ).mat(GTMaterials.Goethite).size(1, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.YellowLimonite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Hematite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Malachite).size(1, 2)
                            }
                    }
            }
    }

    // 铍
    val BERYLLIUM_VEIN_AD: GTOreDefinition = create(
        "beryllium_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(30).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.MERCURY, GTNNWorld.VENUS).heightRangeUniform(5, 30)
            .discardChanceOnAirExposure(0f).layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Beryllium).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Emerald).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Thorium).size(1, 1)
                            }
                    }
            }
    }

    // 黝铜
    val TETRAHEDRITE_VEIN_AD: GTOreDefinition = create(
        "tetrahedrite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(70).layer(GTNNWorldGenLayers.AD).density(0.3f)
            .dimensions(GTNNWorld.MARS, GTNNWorld.VENUS).heightRangeUniform(80, 120).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    4
                                ).mat(GTMaterials.Tetrahedrite).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Copper).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Stibnite).size(1, 1)
                            }
                    }
            }
    }

    // 盐
    val SALTS_VEIN_AD: GTOreDefinition = create(
        "salts_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(30).weight(50).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.MARS)
            .heightRangeUniform(30, 70).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.RockSalt).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Salt).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Lepidolite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Spodumene).size(1, 1)
                            }
                    }
            }
    }

    // 硅岩火星
    val NAQUADAH_VEIN_AD_MARS: GTOreDefinition = create(
        "naquadah_vein_ad_mars"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(48).weight(5).layer(GTNNWorldGenLayers.AD).density(0.4f).dimensions(GTNNWorld.MARS)
            .heightRangeUniform(10, 90).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Naquadah).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.NaquadahEnriched).size(1, 2)
                            }
                    }
            }
    }

    // 水星
    // 铬
    val CHROMIUM_VEIN_AD: GTOreDefinition = create(
        "chromium_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(5).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(-15, 15).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Chromium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Tungsten).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Molybdenum).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Manganese).size(1, 2)
                            }
                    }
            }
    }

    // 铀-238
    val URANIUM238_VEIN_AD: GTOreDefinition = create(
        "uranium238_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(12).weight(5).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(65, 120).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Uranium238).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Plutonium239).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Thorium).size(1, 2)
                            }
                    }
            }
    }

    // 菱镁矿
    val MAGNESITE_VEIN_AD: GTOreDefinition = create(
        "magnesite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(55).layer(GTNNWorldGenLayers.AD).density(0.4f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(35, 65).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Magnesite).size(1, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Hematite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Sulfur).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Opal).size(1, 2)
                            }
                    }
            }
    }

    // 铂
    val PLATINUM_VEIN_AD: GTOreDefinition = create(
        "platinum_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(10).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(-5, 25).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Platinum).size(1, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Chromium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Cooperite).size(1, 2)
                            }
                    }
            }
    }

    // 青金石
    val LAPIS_VEIN_AD: GTOreDefinition = create(
        "lapis_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(40).layer(GTNNWorldGenLayers.AD).density(0.3f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(20, 50).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Lazurite).size(1, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Sodalite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Lapis).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Calcite).size(1, 1)
                            }
                    }
            }
    }

    // 橄榄石
    val OLIVINE_VEIN_AD: GTOreDefinition = create(
        "olivine_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(30).weight(30).layer(GTNNWorldGenLayers.AD).density(0.25f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(-20, 10).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Bentonite).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Magnetite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Olivine).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.GlauconiteSand).size(1, 1)
                            }
                    }
            }
    }

    // 锰
    val MANGANESE_VEIN_AD: GTOreDefinition = create(
        "manganese_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(20).layer(GTNNWorldGenLayers.AD).density(0.2f)
            .dimensions(GTNNWorld.MERCURY, GTNNWorld.GLACIO).heightRangeUniform(-30, 0).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Grossular).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Spessartine).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Pyrolusite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Tantalite).size(1, 2)
                            }
                    }
            }
    }

    //
    val LUBRICANT_VEIN_AD: GTOreDefinition = create(
        "lubricant_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(25).weight(40).layer(GTNNWorldGenLayers.AD).density(0.25f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(0, 50).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Soapstone).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Talc).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.GlauconiteSand).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Pentlandite).size(1, 1)
                            }
                    }
            }
    }

    // 蓝石
    val SALTPETER_VEIN_AD: GTOreDefinition = create(
        "saltpeter_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(30).weight(40).layer(GTNNWorldGenLayers.AD).density(0.25f).dimensions(GTNNWorld.MERCURY)
            .heightRangeUniform(5, 45).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Saltpeter).size(2, 4)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Diatomite).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Electrotine).size(1, 1)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Alunite).size(1, 1)
                            }
                    }
            }
    }

    // 金星
    // 耐热合金
    val CALORITE_VEIN_AD: GTOreDefinition = create(
        "calorite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(30).layer(GTNNWorldGenLayers.AD).density(0.3f).dimensions(GTNNWorld.VENUS)
            .heightRangeUniform(5, 40).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTNNMaterials.Calorite).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Amethyst).size(1, 2)
                            }
                    } //.layer(l -> l.weight(1).state(() -> Block.getBlock("forbidden_arcanus:stella_arcanum").defaultBlockState()))
            }
    }

    // 金红石
    val RUTILE_VEIN_AD: GTOreDefinition = create(
        "rutile_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(18).weight(8).layer(GTNNWorldGenLayers.AD).density(0.4f).dimensions(GTNNWorld.VENUS)
            .heightRangeUniform(-15, 20).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Rutile).size(1, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Titanium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Bauxite).size(1, 2)
                            }
                    }
            }
    }

    // 铱
    val IRIDIUM_VEIN_AD: GTOreDefinition = create(
        "iridium_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(10).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.VENUS)
            .heightRangeUniform(-5, 40).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Nickel).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Iridium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Palladium).size(1, 2)
                            }
                    }
            }
    }

    // 软锰
    val PYROLUSITE_VEIN_AD: GTOreDefinition = create(
        "pyrolusite_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(10).layer(GTNNWorldGenLayers.AD).density(0.4f).dimensions(GTNNWorld.VENUS)
            .heightRangeUniform(0, 30).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Pyrolusite).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Apatite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Tantalite).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Pyrochlore).size(1, 1)
                            }
                    }
            }
    }

    // 硅岩
    val NAQUADAH_VEIN_AD: GTOreDefinition = create(
        "naquadah_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(48).weight(30).layer(GTNNWorldGenLayers.AD).density(0.4f).dimensions(GTNNWorld.VENUS)
            .heightRangeUniform(10, 90).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Naquadah).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.NaquadahEnriched).size(1, 2)
                            }
                    }
            }
    }

    // Glacio
    // 锇
    val OSMIUM_VEIN_AD: GTOreDefinition = create(
        "osmium_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(10).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.GLACIO)
            .heightRangeUniform(-5, 30).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    3
                                ).mat(GTMaterials.Nickel).size(2, 3)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Osmium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Iridium).size(1, 1)
                            }
                    }
            }
    }

    // 中子素
    val NEUTRONIUM_VEIN_AD: GTOreDefinition = create(
        "neutronium_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(24).weight(10).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.GLACIO)
            .heightRangeUniform(-50, -10).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Neutronium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTNNMaterials.InfinityCatalyst).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Naquadria).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Titanium).size(1, 2)
                            }
                    }
            }
    }

    // 铌
    val NIOBIUM_VEIN_AD: GTOreDefinition = create(
        "niobium_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(60).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.GLACIO)
            .heightRangeUniform(-50, -10).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Niobium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Iridium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    1
                                ).mat(GTMaterials.Gallium).size(1, 1)
                            }
                    }
            }
    }

    // 钍
    val THORIUM_VEIN_AD: GTOreDefinition = create(
        "thorium_vein_ad"
    ) { vein: GTOreDefinition ->
        vein.clusterSize(36).weight(60).layer(GTNNWorldGenLayers.AD).density(0.2f).dimensions(GTNNWorld.GLACIO)
            .heightRangeUniform(-10, 30).discardChanceOnAirExposure(0f)
            .layeredVeinGenerator { generator: LayeredVeinGenerator ->
                generator.buildLayerPattern { pattern: GTLayerPattern.Builder ->
                        pattern.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Thorium).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Uranium235).size(1, 2)
                            }.layer { l: GTLayerPattern.Layer.Builder ->
                                l.weight(
                                    2
                                ).mat(GTMaterials.Plutonium241).size(1, 2)
                            }
                    }
            }
    }

    private fun twilightForest(): ResourceLocation {
        return if (isTwilightForestLoaded()) GTNNWorld.TWILIGHT_FOREST else BuiltinDimensionTypes.OVERWORLD.location()
    }

    fun create(name: String?, config: Consumer<GTOreDefinition>?, alwaysCreate: Boolean): GTOreDefinition {
        return if (alwaysCreate) {
            GTOres.create(id(name!!), config)
        } else GTOres.blankOreDefinition()
    }

    fun create(name: String?, config: Consumer<GTOreDefinition>?): GTOreDefinition {
        return if (isAdAstraLoaded()) {
            GTOres.create(id(name!!), config)
        } else GTOres.blankOreDefinition()
    }

    fun init() {
    }
}
