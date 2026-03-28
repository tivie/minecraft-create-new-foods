package pt.tivie.create_new_foods.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import pt.tivie.create_new_foods.CreateNewFoods;

public class WorldGenInit {

    public static void load() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        BiomeKeys.JUNGLE,
                        BiomeKeys.BAMBOO_JUNGLE,
                        BiomeKeys.MANGROVE_SWAMP,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.FLOWER_FOREST
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, CreateNewFoods.id("vanilla_orchid"))
        );
    }
}