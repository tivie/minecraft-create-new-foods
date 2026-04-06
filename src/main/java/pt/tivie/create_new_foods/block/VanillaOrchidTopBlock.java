package pt.tivie.create_new_foods.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import pt.tivie.create_new_foods.init.BlockInit;
import pt.tivie.create_new_foods.init.ItemInit;

public class VanillaOrchidTopBlock extends Block {

    public VanillaOrchidTopBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(ItemInit.VANILLA_BEAN);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.down());
        return below.isOf(BlockInit.VANILLA_BEAN) && below.get(VanillaOrchidBlock.AGE) == VanillaOrchidBlock.MAX_AGE;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        if (direction == Direction.DOWN) {
            if (!neighborState.isOf(BlockInit.VANILLA_BEAN) || neighborState.get(VanillaOrchidBlock.AGE) != VanillaOrchidBlock.MAX_AGE) {
                return Blocks.AIR.getDefaultState();
            }
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // Reset the lower block to age 1 so the plant survives being broken from the top
        BlockPos lowerPos = pos.down();
        BlockState lowerState = world.getBlockState(lowerPos);
        if (lowerState.isOf(BlockInit.VANILLA_BEAN) && lowerState.get(VanillaOrchidBlock.AGE) == VanillaOrchidBlock.MAX_AGE) {
            world.setBlockState(lowerPos, lowerState.with(VanillaOrchidBlock.AGE, 1), Block.NOTIFY_LISTENERS);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        BlockPos lowerPos = pos.down();
        BlockState lowerState = world.getBlockState(lowerPos);
        if (lowerState.isOf(BlockInit.VANILLA_BEAN) && lowerState.get(VanillaOrchidBlock.AGE) == VanillaOrchidBlock.MAX_AGE) {
            if (world instanceof ServerWorld serverWorld) {
                int count = 1 + serverWorld.random.nextInt(2);
                Block.dropStack(serverWorld, pos, new ItemStack(ItemInit.VANILLA_BEAN, count));
                serverWorld.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS,
                        1.0F, 0.8F + serverWorld.random.nextFloat() * 0.4F);
                BlockState newLowerState = lowerState.with(VanillaOrchidBlock.AGE, 1);
                serverWorld.setBlockState(lowerPos, newLowerState, Block.NOTIFY_LISTENERS);
                serverWorld.breakBlock(pos, false);
                serverWorld.emitGameEvent(GameEvent.BLOCK_CHANGE, lowerPos, GameEvent.Emitter.of(player, newLowerState));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}