package pt.tivie.create_new_foods.compat;

import com.zurrtum.create.api.contraption.BlockMovementChecks;
import pt.tivie.create_new_foods.block.AppleLeavesBlock;

public class CreateCompat {

    public static void load() {
        registerMovementChecks();
    }

    // Allow contraptions to pass through apple_leaves without destroying them.
    // The harvester mixin handles the actual apple harvest; we just need to ensure
    // the movement system treats this block as passable.
    private static void registerMovementChecks() {
        BlockMovementChecks.registerMovementAllowedCheck((state, world, pos) -> {
            if (state.getBlock() instanceof AppleLeavesBlock) {
                return BlockMovementChecks.CheckResult.SUCCESS;
            }
            return BlockMovementChecks.CheckResult.PASS;
        });
    }
}