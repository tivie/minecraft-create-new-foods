package pt.tivie.create_new_foods.mixin;

import com.zurrtum.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pt.tivie.create_new_foods.block.AppleLeavesBlock;

@Mixin(value = HarvesterMovementBehaviour.class, remap = false)
public class HarvesterMixin {

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
     * Prevent isValidOther from independently triggering on apple_leaves.
     * isValidCrop above is the sole gate for this block.
     */
    @Inject(method = "isValidOther", at = @At("HEAD"), cancellable = true)
    private void onIsValidOther(World world, BlockPos pos, BlockState state,
                                CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof AppleLeavesBlock) {
            cir.setReturnValue(false);
        }
    }

    /**
     * After the block is destroyed and drops are collected, place the leaf back
     * with has_apples=false instead of returning air or a corrupted state.
     */
    @Inject(method = "cutCrop", at = @At("HEAD"), cancellable = true)
    private void onCutCrop(World world, BlockPos pos, BlockState state,
                           CallbackInfoReturnable<BlockState> cir) {
        if (state.getBlock() instanceof AppleLeavesBlock) {
            cir.setReturnValue(state.with(AppleLeavesBlock.HAS_APPLES, false));
        }
    }
}