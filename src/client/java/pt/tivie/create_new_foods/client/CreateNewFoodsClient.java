package pt.tivie.create_new_foods.client;

import com.zurrtum.create.client.AllFluidConfigs;
import com.zurrtum.create.client.infrastructure.fluid.FluidConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorResolverRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.world.biome.ColorResolver;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import pt.tivie.create_new_foods.init.BlockInit;
import pt.tivie.create_new_foods.init.FluidInit;

public class CreateNewFoodsClient implements ClientModInitializer {

    // Must be a registered static instance — ClientWorld.getColor() rejects unregistered lambdas
    private static final ColorResolver FOLIAGE_COLOR_RESOLVER = (biome, x, z) -> biome.getFoliageColor();


    private void registerFluid(com.zurrtum.create.infrastructure.fluids.FlowableFluid fluid, String name, int fogColor, float fogDistance) {
        FluidRenderHandlerRegistry.INSTANCE.register(
            fluid, fluid.getFlowing(),
            new SimpleFluidRenderHandler(
                Identifier.of("create_new_foods", "fluid/" + name + "_still"),
                Identifier.of("create_new_foods", "fluid/" + name + "_flow")
            )
        );
        Identifier id = Registries.FLUID.getId(fluid).withPrefixedPath("fluid/");
        FluidConfig config = new FluidConfig(
            () -> MinecraftClient.getInstance().getAtlasManager()
                .getSprite(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, id.withSuffixedPath("_still"))),
            () -> MinecraftClient.getInstance().getAtlasManager()
                .getSprite(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, id.withSuffixedPath("_flow"))),
            component -> -1,
            () -> fogDistance,
            fogColor
        );
        AllFluidConfigs.ALL.put(fluid, config);
        AllFluidConfigs.ALL.put(fluid.getFlowing(), config);
    }

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                BlockInit.VANILLA_BEAN,
                BlockInit.VANILLA_ORCHID_TOP,
                BlockInit.APPLE_SAPLING,
                BlockInit.ORANGE_SAPLING);

        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT_MIPPED,
                BlockInit.APPLE_LEAVES,
                BlockInit.ORANGE_LEAVES);

        // Register resolver so ClientWorld can cache it, then use it for block tinting
        ColorResolverRegistry.register(FOLIAGE_COLOR_RESOLVER);
        ColorProviderRegistry.BLOCK.register(
                (state, world, pos, tintIndex) -> world != null && pos != null
                        ? world.getColor(pos, FOLIAGE_COLOR_RESOLVER)
                        : FoliageColors.DEFAULT,
                BlockInit.APPLE_LEAVES,
                BlockInit.ORANGE_LEAVES);

        registerFluid(FluidInit.PUMPKIN_PULP,    "pumpkin_pulp",    0xC4621D, 96.0f / 16f);
        registerFluid(FluidInit.SWEETBERRY_PULP, "sweetberry_pulp", 0x8B1919, 96.0f / 16f);
        registerFluid(FluidInit.VANILLA,         "vanilla",         0xE8D5A0, 96.0f / 8f);
        registerFluid(FluidInit.MELON_PULP,      "melon_pulp",      0xD4524A, 96.0f / 16f);
        registerFluid(FluidInit.APPLE_PULP,       "apple_pulp",       0xC8860A, 96.0f / 16f);
        registerFluid(FluidInit.GLOW_BERRY_PULP, "glow_berry_pulp", 0x7DC23E, 96.0f / 8f);
        registerFluid(FluidInit.ORANGE_PULP,      "orange_pulp",      0xE8650A, 96.0f / 16f);
    }
}
