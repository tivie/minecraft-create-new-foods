package pt.tivie.create_new_foods.mixin;

import com.zurrtum.create.content.contraptions.ContraptionCollider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pt.tivie.create_new_foods.block.HarvestableLeavesBlock;

@Mixin(value = ContraptionCollider.class, remap = false)
public class ContraptionColliderMixin {

    /**
     * Treat all harvestable fruit leaves as non-solid to Create's contraption/world collision checks.
     * This lets the flying contraption pass through the canopy instead of only the harvester actor block.
     */
    @Redirect(
            method = "isCollidingWithWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"
            )
    )
    private static BlockState createNewFoods$ignoreFruitLeavesForContraptions(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof HarvestableLeavesBlock) {
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }
}