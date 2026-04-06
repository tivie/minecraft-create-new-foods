package pt.tivie.create_new_foods.mixin;

import com.zurrtum.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.zurrtum.create.api.behaviour.movement.MovementBehaviour;
import com.zurrtum.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pt.tivie.create_new_foods.block.HarvestableLeavesBlock;

@Mixin(value = HarvesterMovementBehaviour.class, remap = false)
public class HarvesterMixin {

    /**
     * Custom-harvest fruit leaves without running Create's default destroy/replant flow.
     * Fruit leaves are not normal crop blocks — we want pure "pick fruit and keep leaves" behaviour.
     */
    @Inject(method = "visitNewPosition", at = @At("HEAD"), cancellable = true)
    private void onVisitNewPosition(MovementContext context, BlockPos pos, CallbackInfo ci) {
        World world = context.world;
        if (world.isClient()) {
            return;
        }

        BlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof HarvestableLeavesBlock fruitLeaves)) {
            return;
        }

        if (!state.get(fruitLeaves.getHasFruitProperty())) {
            ci.cancel();
            return;
        }

        ((MovementBehaviour) (Object) this).collectOrDropItem(context, new ItemStack(fruitLeaves.getFruitItem()));
        world.setBlockState(pos, state.with(fruitLeaves.getHasFruitProperty(), false));
        world.playSound(null, pos, SoundEvents.BLOCK_GRASS_HIT, SoundCategory.BLOCKS,
                1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        ci.cancel();
    }

    /**
     * Allow harvesting only when fruit is present; block otherwise to prevent
     * the harvester from treating LeavesBlock.DISTANCE (an IntProperty) as a crop age.
     */
    @Inject(method = "isValidCrop", at = @At("HEAD"), cancellable = true)
    private void onIsValidCrop(World world, BlockPos pos, BlockState state,
                               CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof HarvestableLeavesBlock fruitLeaves) {
            cir.setReturnValue(state.get(fruitLeaves.getHasFruitProperty()));
        }
    }

    /**
     * Mark fruit leaves as valid harvester targets so terrain collision lets the
     * contraption pass through them.
     */
    @Inject(method = "isValidOther", at = @At("HEAD"), cancellable = true)
    private void onIsValidOther(World world, BlockPos pos, BlockState state,
                                CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof HarvestableLeavesBlock) {
            cir.setReturnValue(true);
        }
    }
}