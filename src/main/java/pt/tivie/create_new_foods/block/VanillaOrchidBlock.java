package pt.tivie.create_new_foods.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import pt.tivie.create_new_foods.init.BlockInit;
import pt.tivie.create_new_foods.init.ItemInit;

public class VanillaOrchidBlock extends PlantBlock implements Fertilizable {

    public static final MapCodec<VanillaOrchidBlock> CODEC = createCodec(VanillaOrchidBlock::new);
    public static final IntProperty AGE = Properties.AGE_3;
    public static final int MAX_AGE = 3;
    private static final int MIN_LIGHT = 7;

    public VanillaOrchidBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public MapCodec<VanillaOrchidBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(ItemInit.VANILLA_BEAN);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = state.get(AGE);
        if (age < MAX_AGE && world.getBaseLightLevel(pos.up(), 0) >= MIN_LIGHT && random.nextInt(10) == 0) {
            if (age == MAX_AGE - 1) {
                BlockPos topPos = pos.up();
                if (world.getBlockState(topPos).isAir()) {
                    BlockState newState = state.with(AGE, MAX_AGE);
                    world.setBlockState(pos, newState, NOTIFY_LISTENERS);
                    world.setBlockState(topPos, BlockInit.VANILLA_ORCHID_TOP.getDefaultState(), NOTIFY_LISTENERS);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newState));
                }
            } else {
                BlockState newState = state.with(AGE, age + 1);
                world.setBlockState(pos, newState, NOTIFY_LISTENERS);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newState));
            }
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(AGE) == MAX_AGE) {
            if (world instanceof ServerWorld serverWorld) {
                int count = 1 + serverWorld.random.nextInt(2);
                Block.dropStack(serverWorld, pos, new ItemStack(ItemInit.VANILLA_BEAN, count));
                serverWorld.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS,
                        1.0F, 0.8F + serverWorld.random.nextFloat() * 0.4F);
                BlockState newState = state.with(AGE, 1);
                serverWorld.setBlockState(pos, newState, NOTIFY_LISTENERS);
                serverWorld.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), NOTIFY_ALL | SKIP_DROPS);
                serverWorld.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, newState));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(AGE) == MAX_AGE) {
            BlockPos topPos = pos.up();
            if (world.getBlockState(topPos).isOf(BlockInit.VANILLA_ORCHID_TOP)) {
                world.setBlockState(topPos, Blocks.AIR.getDefaultState(), NOTIFY_ALL | SKIP_DROPS);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    // Fertilizable

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int nextAge = Math.min(MAX_AGE, state.get(AGE) + 1);
        if (nextAge == MAX_AGE) {
            BlockPos topPos = pos.up();
            if (world.getBlockState(topPos).isAir()) {
                world.setBlockState(pos, state.with(AGE, MAX_AGE), NOTIFY_LISTENERS);
                world.setBlockState(topPos, BlockInit.VANILLA_ORCHID_TOP.getDefaultState(), NOTIFY_LISTENERS);
            }
        } else {
            world.setBlockState(pos, state.with(AGE, nextAge), NOTIFY_LISTENERS);
        }
    }
}