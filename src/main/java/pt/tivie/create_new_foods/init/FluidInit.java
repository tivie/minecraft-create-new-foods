package pt.tivie.create_new_foods.init;

import com.zurrtum.create.infrastructure.fluids.FlowableFluid;
import com.zurrtum.create.infrastructure.fluids.FluidBlock;
import com.zurrtum.create.infrastructure.fluids.FluidEntry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import pt.tivie.create_new_foods.CreateNewFoods;

public class FluidInit {

    public static void load() {}

    public static FluidBlock registerFluidBlock(String name, FlowableFluid fluid, MapColor mapColor) {
        Identifier id = CreateNewFoods.id(name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        FluidBlock block = Registry.register(Registries.BLOCK, key,
            new FluidBlock(fluid, AbstractBlock.Settings.copy(Blocks.WATER).mapColor(mapColor).registryKey(key)));
        fluid.getEntry().block = block;
        return block;
    }

    public static FlowableFluid registerFluid(String name) {
        Identifier id = CreateNewFoods.id(name);
        RegistryKey<Fluid> still_key = RegistryKey.of(RegistryKeys.FLUID, id);
        RegistryKey<Fluid> flowing_key = RegistryKey.of(RegistryKeys.FLUID, id.withPrefixedPath("flowing_"));
        FluidEntry entry = new FluidEntry();
        entry.still = new FlowableFluid.Still(entry);
        entry.flowing = new FlowableFluid.Flowing(entry);
        Registry.register(Registries.FLUID, still_key.getValue(), entry.still);
        Registry.register(Registries.FLUID, flowing_key.getValue(), entry.flowing);
        return entry.still;
    }

    public static final FlowableFluid PUMPKIN_PULP = registerFluid("pumpkin_pulp");
    public static final FluidBlock PUMPKIN_PULP_BLOCK = registerFluidBlock("pumpkin_pulp", PUMPKIN_PULP, MapColor.ORANGE);

    public static final FlowableFluid SWEETBERRY_PULP = registerFluid("sweetberry_pulp");
    public static final FluidBlock SWEETBERRY_PULP_BLOCK = registerFluidBlock("sweetberry_pulp", SWEETBERRY_PULP, MapColor.RED);

    public static final FlowableFluid VANILLA = registerFluid("vanilla");
    public static final FluidBlock VANILLA_BLOCK = registerFluidBlock("vanilla", VANILLA, MapColor.YELLOW);

    public static final FlowableFluid MELON_PULP = registerFluid("melon_pulp");
    public static final FluidBlock MELON_PULP_BLOCK = registerFluidBlock("melon_pulp", MELON_PULP, MapColor.RED);

    public static final FlowableFluid APPLE_PULP = registerFluid("apple_pulp");
    public static final FluidBlock APPLE_PULP_BLOCK = registerFluidBlock("apple_pulp", APPLE_PULP, MapColor.GOLD);

    public static final FlowableFluid GLOW_BERRY_PULP = registerFluid("glow_berry_pulp");
    public static final FluidBlock GLOW_BERRY_PULP_BLOCK = registerFluidBlock("glow_berry_pulp", GLOW_BERRY_PULP, MapColor.LIME);

    public static final FlowableFluid ORANGE_PULP = registerFluid("orange_pulp");
    public static final FluidBlock ORANGE_PULP_BLOCK = registerFluidBlock("orange_pulp", ORANGE_PULP, MapColor.ORANGE);

}
