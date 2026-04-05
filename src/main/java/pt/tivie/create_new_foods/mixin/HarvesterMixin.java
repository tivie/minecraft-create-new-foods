package pt.tivie.create_new_foods.mixin;

import com.zurrtum.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.zurrtum.create.api.behaviour.movement.MovementBehaviour;
import com.zurrtum.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import pt.tivie.create_new_foods.block.AppleLeavesBlock;

@Mixin(value = HarvesterMovementBehaviour.class, remap = false)
public class HarvesterMixin {

    /**
     * Custom-harvest apples without running Create's default destroy/replant flow.
     * Apple leaves are not a normal crop block, so breaking first causes leaf drops and
     * only restores the block afterwards. We want pure "pick fruit and keep leaves" behavior.
     */
    @Inject(method = "visitNewPosition", at = @At("HEAD"), cancellable = true)
    private void onVisitNewPosition(MovementContext context, BlockPos pos, CallbackInfo ci) {
        World world = context.world;
        if (world.isClient()) {
            return;
        }

        BlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof AppleLeavesBlock)) {
            return;
        }

        if (!state.get(AppleLeavesBlock.HAS_APPLES)) {
            ci.cancel();
            return;
        }

        ((MovementBehaviour) (Object) this).collectOrDropItem(context, new ItemStack(Items.APPLE));
        world.setBlockState(pos, state.with(AppleLeavesBlock.HAS_APPLES, false));
        world.playSound(null, pos, SoundEvents.BLOCK_GRASS_HIT, SoundCategory.BLOCKS,
                1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        ci.cancel();
    }

    /**
     * Allow harvesting only when has_apples=true; block when false to prevent
     * the harvester from treating LeavesBlock.DISTANCE (an IntProperty) as a crop age.
     */
    @Inject(method = "isValidCrop", at = @At("HEAD"), cancellable = true)
    private void onIsValidCrop(World world, BlockPos pos, BlockState state,
                               CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof AppleLeavesBlock) {
            cir.setReturnValue(state.get(AppleLeavesBlock.HAS_APPLES));
        }
    }

    /**
     * Mark apple leaves as a valid harvester target so terrain collision lets the
     * contraption pass through them. visitNewPosition above cancels the default
     * break logic for both states and performs custom harvesting only when ripe.
     */
    @Inject(method = "isValidOther", at = @At("HEAD"), cancellable = true)
    private void onIsValidOther(World world, BlockPos pos, BlockState state,
                                CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof AppleLeavesBlock) {
            cir.setReturnValue(true);
        }
    }
}
