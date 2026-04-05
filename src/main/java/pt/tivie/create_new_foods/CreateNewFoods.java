package pt.tivie.create_new_foods;

import com.zurrtum.create.api.contraption.BlockMovementChecks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.tivie.create_new_foods.block.AppleLeavesBlock;
import pt.tivie.create_new_foods.compat.TffCompat;
import pt.tivie.create_new_foods.init.BlockInit;
import pt.tivie.create_new_foods.init.FluidInit;
import pt.tivie.create_new_foods.init.ItemGroupInit;
import pt.tivie.create_new_foods.init.ItemInit;
import pt.tivie.create_new_foods.init.WorldGenInit;

public class CreateNewFoods implements ModInitializer {

    public static final String MOD_ID = "create_new_foods";
    public static final Logger LOGGER = LoggerFactory.getLogger("CreateNewFoods");

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Create: New Foods mod");
        BlockInit.load();
        FluidInit.load();
        ItemGroupInit.load();
        ItemInit.load();
        WorldGenInit.load();
        TffCompat.load();
        registerMovementChecks();
    }

    private void registerMovementChecks() {
        // Allow contraptions to pass through apple_leaves without destroying them.
        // The harvester mixin handles the actual apple harvest; we just need to ensure
        // the movement system treats this block as passable.
        BlockMovementChecks.registerMovementAllowedCheck((state, world, pos) -> {
            if (state.getBlock() instanceof AppleLeavesBlock) {
                return BlockMovementChecks.CheckResult.SUCCESS;
            }
            return BlockMovementChecks.CheckResult.PASS;
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
