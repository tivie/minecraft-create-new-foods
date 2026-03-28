package pt.tivie.create_new_foods.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import pt.tivie.create_new_foods.CreateNewFoods;
import pt.tivie.create_new_foods.block.VanillaOrchidBlock;
import pt.tivie.create_new_foods.block.VanillaOrchidTopBlock;

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
}