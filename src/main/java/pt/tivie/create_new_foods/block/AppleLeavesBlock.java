package pt.tivie.create_new_foods.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class AppleLeavesBlock extends TintedParticleLeavesBlock implements Fertilizable {

    public static final MapCodec<AppleLeavesBlock> CODEC = createCodec(AppleLeavesBlock::new);
    public static final BooleanProperty HAS_APPLES = BooleanProperty.of("has_apples");

    // Same particle emission rate as oak leaves
    private static final float LEAF_PARTICLE_CHANCE = 0.02f;
    // ~1% chance per random tick to grow apples (roughly every few in-game days per leaf block)
    private static final int APPLE_GROW_CHANCE = 100;

    public AppleLeavesBlock(Settings settings) {
        super(LEAF_PARTICLE_CHANCE, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HAS_APPLES, false));
    }

    @Override
    public MapCodec<AppleLeavesBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder); // adds DISTANCE, PERSISTENT, WATERLOGGED
        builder.add(HAS_APPLES);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        // Tick when apples can still grow, or when the parent leaf decay logic needs ticking
        return !state.get(HAS_APPLES) || super.hasRandomTicks(state);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random); // handles leaf decay
        if (!state.get(HAS_APPLES) && random.nextInt(APPLE_GROW_CHANCE) == 0) {
            world.setBlockState(pos, state.with(HAS_APPLES, true), NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(HAS_APPLES)) {
            if (world instanceof ServerWorld serverWorld) {
                Block.dropStack(serverWorld, pos, new ItemStack(Items.APPLE));
                serverWorld.playSound(null, pos, SoundEvents.BLOCK_GRASS_HIT, SoundCategory.BLOCKS,
                        1.0F, 0.8F + serverWorld.random.nextFloat() * 0.4F);
                serverWorld.setBlockState(pos, state.with(HAS_APPLES, false), NOTIFY_LISTENERS);
                serverWorld.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    // Fertilizable — bone meal forces apple growth

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !state.get(HAS_APPLES);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(HAS_APPLES, true), NOTIFY_LISTENERS);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
    }
}