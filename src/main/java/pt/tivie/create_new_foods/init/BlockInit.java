package pt.tivie.create_new_foods.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import pt.tivie.create_new_foods.CreateNewFoods;
import pt.tivie.create_new_foods.block.AppleLeavesBlock;
import pt.tivie.create_new_foods.block.OrangeLeavesBlock;
import pt.tivie.create_new_foods.block.VanillaOrchidBlock;
import pt.tivie.create_new_foods.block.VanillaOrchidTopBlock;

import java.util.Optional;

import java.util.function.Function;

public class BlockInit {

    public static void load() {}

    private static <T extends Block> T registerBlock(String name, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
        Identifier id = CreateNewFoods.id(name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        return Registry.register(Registries.BLOCK, key, factory.apply(settings.registryKey(key)));
    }

    public static final VanillaOrchidBlock VANILLA_BEAN = registerBlock(
            "vanilla_bean",
            VanillaOrchidBlock::new,
            AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)
    );

    public static final VanillaOrchidTopBlock VANILLA_ORCHID_TOP = registerBlock(
            "vanilla_orchid_top",
            VanillaOrchidTopBlock::new,
            AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)
    );

    private static final SaplingGenerator APPLE_TREE_GROWER = new SaplingGenerator(
            "apple_tree",
            Optional.empty(),
            Optional.of(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, CreateNewFoods.id("apple_tree"))),
            Optional.empty()
    );

    public static final AppleLeavesBlock APPLE_LEAVES = registerBlock(
            "apple_leaves",
            AppleLeavesBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
    );

    public static final SaplingBlock APPLE_SAPLING = registerBlock(
            "apple_sapling",
            settings -> new SaplingBlock(APPLE_TREE_GROWER, settings),
            AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)
    );

    private static final SaplingGenerator ORANGE_TREE_GROWER = new SaplingGenerator(
            "orange_tree",
            Optional.empty(),
            Optional.of(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, CreateNewFoods.id("orange_tree"))),
            Optional.empty()
    );

    public static final OrangeLeavesBlock ORANGE_LEAVES = registerBlock(
            "orange_leaves",
            OrangeLeavesBlock::new,
            AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
    );

    public static final SaplingBlock ORANGE_SAPLING = registerBlock(
            "orange_sapling",
            settings -> new SaplingBlock(ORANGE_TREE_GROWER, settings),
            AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)
    );
}