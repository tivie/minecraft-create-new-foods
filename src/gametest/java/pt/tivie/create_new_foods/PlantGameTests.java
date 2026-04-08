package pt.tivie.create_new_foods;

import com.zurrtum.create.AllBlocks;
import com.zurrtum.create.api.contraption.ContraptionType;
import com.zurrtum.create.content.contraptions.Contraption;
import com.zurrtum.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.zurrtum.create.content.contraptions.behaviour.MovementContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.structure.StructureTemplate.StructureBlockInfo;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import pt.tivie.create_new_foods.block.AppleLeavesBlock;
import pt.tivie.create_new_foods.block.OrangeLeavesBlock;
import pt.tivie.create_new_foods.init.BlockInit;
import pt.tivie.create_new_foods.init.ItemInit;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifies sapling growth and harvestable-leaves behaviour for apple and orange trees.
 */
class PlantGameTests {

    private static final class TestContraption extends Contraption {

        TestContraption() {
            getStorage().initialize();
        }

        @Override
        public boolean assemble(net.minecraft.world.World world, BlockPos pos) {
            return false;
        }

        @Override
        public boolean canBeStabilized(Direction facing, BlockPos localPos) {
            return false;
        }

        @Override
        public ContraptionType getType() {
            return null;
        }
    }

    /**
     * Calls SaplingBlock.grow() twice for both the apple and orange sapling — the first call
     * advances STAGE 0→1, the second (at STAGE 1) invokes SaplingGenerator.generate() to place
     * the tree. After growth the sapling block must have been replaced; if it's still there the
     * configured feature failed to place (unregistered feature, insufficient space, or world gen
     * not loaded correctly).
     * A 7×16×7 area is cleared before planting to ensure the tree feature has room — generated
     * terrain can reach Y=200 in mountain biomes, which would cause TreeFeature to silently abort.
     */
    static void assertSaplingsGrow(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerWorld world = server.getOverworld();
            List<String> fails = new ArrayList<>();

            record SaplingTest(String name, SaplingBlock block, BlockPos base) {}
            var tests = List.of(
                new SaplingTest("apple",  BlockInit.APPLE_SAPLING,  new BlockPos(0,  200, 0)),
                new SaplingTest("orange", BlockInit.ORANGE_SAPLING, new BlockPos(20, 200, 0))
            );

            for (var t : tests) {
                BlockPos dirtPos    = t.base();
                BlockPos saplingPos = t.base().up();

                // Clear a 7×16×7 volume (±3 horizontal, 16 tall from dirt) before planting.
                // The tree feature (trunk 4–6 blocks + blob foliage radius 2, height 3) will
                // silently abort and leave the sapling in place if any block in its path is
                // non-replaceable — which can happen when generated terrain reaches Y=200.
                for (int dx = -3; dx <= 3; dx++) {
                    for (int dz = -3; dz <= 3; dz++) {
                        for (int dy = 0; dy <= 15; dy++) {
                            world.setBlockState(dirtPos.add(dx, dy, dz), Blocks.AIR.getDefaultState());
                        }
                    }
                }

                world.setBlockState(dirtPos,    Blocks.DIRT.getDefaultState());
                world.setBlockState(saplingPos, t.block().getDefaultState());

                // SaplingBlock.grow() is two-step: the first call advances STAGE 0→1,
                // the second call (at STAGE 1) actually invokes SaplingGenerator.generate().
                t.block().grow(world, world.getRandom(), saplingPos, world.getBlockState(saplingPos));
                t.block().grow(world, world.getRandom(), saplingPos, world.getBlockState(saplingPos));

                if (world.getBlockState(saplingPos).getBlock() == t.block()) {
                    fails.add(t.name() + " sapling: still a sapling after grow() — tree feature may not be registered");
                }
            }
            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Sapling growth failures: " + failures);
        }
    }

    /**
     * Calls HarvestableLeavesBlock.grow() (bone meal path) on leaves that have no fruit yet.
     * Asserts the fruit property flips to true for both apple and orange leaves.
     */
    static void assertLeavesFruitWithBonemeal(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerWorld world = server.getOverworld();
            List<String> fails = new ArrayList<>();

            // Apple leaves
            BlockPos applePos = new BlockPos(0, 220, 0);
            world.setBlockState(applePos, Blocks.AIR.getDefaultState());
            world.setBlockState(applePos, BlockInit.APPLE_LEAVES.getDefaultState());
            BlockInit.APPLE_LEAVES.grow(world, world.getRandom(), applePos, world.getBlockState(applePos));
            if (!world.getBlockState(applePos).get(AppleLeavesBlock.HAS_APPLES)) {
                fails.add("apple_leaves: HAS_APPLES not true after grow()");
            }

            // Orange leaves
            BlockPos orangePos = new BlockPos(20, 220, 0);
            world.setBlockState(orangePos, Blocks.AIR.getDefaultState());
            world.setBlockState(orangePos, BlockInit.ORANGE_LEAVES.getDefaultState());
            BlockInit.ORANGE_LEAVES.grow(world, world.getRandom(), orangePos, world.getBlockState(orangePos));
            if (!world.getBlockState(orangePos).get(OrangeLeavesBlock.HAS_ORANGES)) {
                fails.add("orange_leaves: HAS_ORANGES not true after grow()");
            }

            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Leaves bonemeal failures: " + failures);
        }
    }

    /**
     * Places leaves with the fruit property set to true, calls BlockState.onUse() to simulate
     * a right-click, then asserts three things:
     *   1. onUse returns SUCCESS
     *   2. the fruit property is reset to false
     *   3. an ItemEntity carrying the correct fruit spawned within 3 blocks of the leaves
     */
    static void assertLeavesDropFruitOnRightClick(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerWorld world = server.getOverworld();
            ServerPlayerEntity player = server.getPlayerManager().getPlayerList().getFirst();
            List<String> fails = new ArrayList<>();

            record HarvestTest(String name, BlockState state, BlockPos pos, Item expectedDrop) {}
            var tests = List.of(
                new HarvestTest("apple_leaves",
                    BlockInit.APPLE_LEAVES.getDefaultState().with(AppleLeavesBlock.HAS_APPLES, true),
                    new BlockPos(0, 230, 0), Items.APPLE),
                new HarvestTest("orange_leaves",
                    BlockInit.ORANGE_LEAVES.getDefaultState().with(OrangeLeavesBlock.HAS_ORANGES, true),
                    new BlockPos(20, 230, 0), ItemInit.ORANGE)
            );

            for (var t : tests) {
                world.setBlockState(t.pos(), Blocks.AIR.getDefaultState());
                world.setBlockState(t.pos(), t.state());

                BlockHitResult hit = new BlockHitResult(Vec3d.ofCenter(t.pos()), Direction.UP, t.pos(), false);
                ActionResult result = world.getBlockState(t.pos()).onUse(world, player, hit);

                if (result != ActionResult.SUCCESS) {
                    fails.add(t.name() + ": onUse returned " + result + " (expected SUCCESS)");
                    continue;
                }
                if (world.getBlockState(t.pos()).get(t.state().getBlock() == BlockInit.APPLE_LEAVES
                        ? AppleLeavesBlock.HAS_APPLES : OrangeLeavesBlock.HAS_ORANGES)) {
                    fails.add(t.name() + ": fruit property still true after harvest");
                }
                Box box = Box.of(Vec3d.ofCenter(t.pos()), 3, 3, 3);
                if (world.getEntitiesByType(EntityType.ITEM, box, e -> e.getStack().isOf(t.expectedDrop())).isEmpty()) {
                    fails.add(t.name() + ": no " + Registries.ITEM.getId(t.expectedDrop()) + " item entity dropped");
                }
            }
            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Leaves harvest failures: " + failures);
        }
    }

    /**
     * Creates a minimal tree shape for both apple and orange, then runs Create Fly's
     * HarvesterMovementBehaviour over the fruit-bearing leaf block. The leaves must stay
     * in place, their fruit property must be cleared, and the corresponding fruit item
     * must be spawned by the harvester.
     */
    static void assertMechanicalHarvesterHarvestsFruitLeaves(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerWorld world = server.getOverworld();
            List<String> fails = new ArrayList<>();
            HarvesterMovementBehaviour behaviour = new HarvesterMovementBehaviour();

            record HarvestTest(String name, BlockState leaves, BooleanProperty fruitProperty, BlockPos trunkPos, Item expectedDrop) {}
            var tests = List.of(
                new HarvestTest(
                    "apple_tree",
                    BlockInit.APPLE_LEAVES.getDefaultState().with(AppleLeavesBlock.HAS_APPLES, true),
                    AppleLeavesBlock.HAS_APPLES,
                    new BlockPos(0, 240, 0),
                    Items.APPLE
                ),
                new HarvestTest(
                    "orange_tree",
                    BlockInit.ORANGE_LEAVES.getDefaultState().with(OrangeLeavesBlock.HAS_ORANGES, true),
                    OrangeLeavesBlock.HAS_ORANGES,
                    new BlockPos(20, 240, 0),
                    ItemInit.ORANGE
                )
            );

            BlockState harvesterState = AllBlocks.MECHANICAL_HARVESTER.getDefaultState()
                .with(HorizontalFacingBlock.FACING, Direction.EAST);

            for (var t : tests) {
                BlockPos trunkBase = t.trunkPos();
                BlockPos leafPos = trunkBase.up(2);
                Box itemBox = Box.of(Vec3d.ofCenter(leafPos), 4, 4, 4);

                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        for (int dy = 0; dy <= 4; dy++) {
                            world.setBlockState(trunkBase.add(dx, dy, dz), Blocks.AIR.getDefaultState());
                        }
                    }
                }

                world.getEntitiesByType(EntityType.ITEM, itemBox, entity -> true).forEach(Entity::discard);

                world.setBlockState(trunkBase, Blocks.OAK_LOG.getDefaultState());
                world.setBlockState(trunkBase.up(), Blocks.OAK_LOG.getDefaultState());
                world.setBlockState(leafPos, t.leaves());

                MovementContext context = new MovementContext(world, new StructureBlockInfo(BlockPos.ORIGIN, harvesterState, null), new TestContraption());
                context.position = Vec3d.ofCenter(leafPos);
                context.motion = Vec3d.ZERO;
                context.relativeMotion = Vec3d.ZERO;

                behaviour.visitNewPosition(context, leafPos);

                BlockState result = world.getBlockState(leafPos);
                if (!result.isOf(t.leaves().getBlock())) {
                    fails.add(t.name() + ": harvester destroyed the leaves block");
                    continue;
                }
                if (result.get(t.fruitProperty())) {
                    fails.add(t.name() + ": fruit property still true after harvester pass");
                }
                if (world.getEntitiesByType(EntityType.ITEM, itemBox, entity -> entity.getStack().isOf(t.expectedDrop())).isEmpty()) {
                    fails.add(t.name() + ": no dropped " + Registries.ITEM.getId(t.expectedDrop()) + " found after harvester pass");
                }
            }

            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Harvester fruit harvest failures: " + failures);
        }
    }
}
