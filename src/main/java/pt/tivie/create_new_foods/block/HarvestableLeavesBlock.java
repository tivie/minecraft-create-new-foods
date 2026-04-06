package pt.tivie.create_new_foods.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public abstract class HarvestableLeavesBlock extends TintedParticleLeavesBlock implements Fertilizable {

    protected static final float LEAF_PARTICLE_CHANCE = 0.02f;
    protected static final int FRUIT_GROW_CHANCE = 20;

    protected HarvestableLeavesBlock(Settings settings) {
        super(LEAF_PARTICLE_CHANCE, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(getHasFruitProperty(), false));
    }

    /** The block state property tracking whether fruit is present. */
    public abstract BooleanProperty getHasFruitProperty();

    /** The item dropped when the player harvests ripe fruit. */
    public abstract Item getFruitItem();

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder); // adds DISTANCE, PERSISTENT, WATERLOGGED
        builder.add(getHasFruitProperty());
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !state.get(getHasFruitProperty()) || super.hasRandomTicks(state);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random); // handles leaf decay
        if (!state.get(getHasFruitProperty()) && random.nextInt(FRUIT_GROW_CHANCE) == 0) {
            world.setBlockState(pos, state.with(getHasFruitProperty(), true), NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(getHasFruitProperty())) {
            if (world instanceof ServerWorld serverWorld) {
                Block.dropStack(serverWorld, pos, new ItemStack(getFruitItem()));
                serverWorld.playSound(null, pos, SoundEvents.BLOCK_GRASS_HIT, SoundCategory.BLOCKS,
                        1.0F, 0.8F + serverWorld.random.nextFloat() * 0.4F);
                serverWorld.setBlockState(pos, state.with(getHasFruitProperty(), false), NOTIFY_LISTENERS);
                serverWorld.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    // Fertilizable — bone meal forces fruit growth

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !state.get(getHasFruitProperty());
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(getHasFruitProperty(), true), NOTIFY_LISTENERS);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
    }
}