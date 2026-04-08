package pt.tivie.create_new_foods;

import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import pt.tivie.create_new_foods.init.FluidInit;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifies that all mod pulp fluids can be inserted into and read back from a Create fluid tank.
 */
class FluidGameTests {

    // All registered pulp fluids — keep in sync with FluidInit.java
    private static final List<Fluid> ALL_PULP_FLUIDS = List.of(
        FluidInit.PUMPKIN_PULP,
        FluidInit.SWEETBERRY_PULP,
        FluidInit.VANILLA,
        FluidInit.MELON_PULP,
        FluidInit.APPLE_PULP,
        FluidInit.GLOW_BERRY_PULP,
        FluidInit.ORANGE_PULP
    );

    /**
     * For each pulp fluid, places a fresh create:fluid_tank in the world, inserts one bucket
     * of the fluid via Fabric's FluidStorage API, then reads the storage back and asserts:
     * - the tank accepted the fluid (inserted > 0)
     * - the tank reports the correct FluidVariant after insertion
     */
    static void assertPulpFluidsCanBeStoredInCreateTank(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerWorld world = server.getOverworld();
            Block tankBlock = Registries.BLOCK.get(Identifier.of("create", "fluid_tank"));
            // Y=200 is safely above any generated terrain
            BlockPos pos = new BlockPos(0, 200, 0);
            List<String> fails = new ArrayList<>();

            for (Fluid fluid : ALL_PULP_FLUIDS) {
                String fluidName = Registries.FLUID.getId(fluid).toString();

                // Clear to AIR first so Minecraft destroys the block entity, then place fresh tank
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.setBlockState(pos, tankBlock.getDefaultState());

                var storage = FluidStorage.SIDED.find(world, pos, Direction.UP);
                if (storage == null) {
                    fails.add("create:fluid_tank does not expose FluidStorage — test cannot continue");
                    break;
                }

                // Insert exactly one bucket worth of fluid
                FluidVariant variant = FluidVariant.of(fluid);
                long inserted;
                try (Transaction tx = Transaction.openOuter()) {
                    inserted = storage.insert(variant, FluidConstants.BUCKET, tx);
                    tx.commit();
                }
                if (inserted == 0) {
                    fails.add(fluidName + ": tank refused insertion (inserted 0 droplets)");
                    continue;
                }

                // Read back and verify the stored fluid matches what was inserted
                boolean found = false;
                var readStorage = FluidStorage.SIDED.find(world, pos, Direction.UP);
                if (readStorage == null) {
                    fails.add(fluidName + ": tank lost FluidStorage after insertion");
                    continue;
                }
                for (var view : readStorage) {
                    if (!view.isResourceBlank() && view.getResource().isOf(fluid)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    fails.add(fluidName + ": not readable from tank after insertion");
                }
            }

            // Clean up
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Create tank fluid storage failures: " + failures);
        }
    }
}