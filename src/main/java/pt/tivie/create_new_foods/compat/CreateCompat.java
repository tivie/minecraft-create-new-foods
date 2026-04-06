package pt.tivie.create_new_foods.compat;

import com.zurrtum.create.api.contraption.BlockMovementChecks;
import pt.tivie.create_new_foods.block.HarvestableLeavesBlock;

public class CreateCompat {

    public static void load() {
        registerMovementChecks();
    }

    // Allow contraptions to pass through all harvestable fruit leaves without destroying them.
    // The harvester mixin handles the actual fruit harvest; we just need to ensure
    // the movement system treats these blocks as passable.
    private static void registerMovementChecks() {
        BlockMovementChecks.registerMovementAllowedCheck((state, world, pos) -> {
            if (state.getBlock() instanceof HarvestableLeavesBlock) {
                return BlockMovementChecks.CheckResult.SUCCESS;
            }
            return BlockMovementChecks.CheckResult.PASS;
        });
    }
}