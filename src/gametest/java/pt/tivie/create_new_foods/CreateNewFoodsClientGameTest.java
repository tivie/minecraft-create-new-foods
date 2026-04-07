package pt.tivie.create_new_foods;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import pt.tivie.create_new_foods.init.FluidInit;

import java.util.ArrayList;
import java.util.List;

public class CreateNewFoodsClientGameTest implements FabricClientGameTest {

    // One entry per registered item — keep in sync with ItemInit.java
    private static final List<String> ALL_ITEMS = List.of(
        // Fruit
        "orange",
        // Ice cream
        "icecream_cone",
        "icecream",
        "icecream_chocolate",
        "icecream_pumpkin",
        "icecream_sweetberry",
        "icecream_vanilla",
        "icecream_melon",
        "icecream_apple",
        "icecream_glow_berry",
        "icecream_orange",
        // Cupcakes
        "cupcake_base",
        "cupcake_chocolate",
        "cupcake_pumpkin",
        "cupcake_sweetberry",
        "cupcake_vanilla",
        "cupcake_melon",
        "cupcake_apple",
        "cupcake_glow_berry",
        "cupcake_orange",
        // Juices
        "juice_apple",
        "juice_glow_berry",
        "juice_melon",
        "juice_pumpkin",
        "juice_sweetberry",
        "juice_orange",
        "juice_tutti_frutti",
        // Dairy
        "yogurt",
        "yogurt_pumpkin",
        "yogurt_sweetberry",
        "yogurt_vanilla",
        "yogurt_melon",
        "yogurt_apple",
        "yogurt_glow_berry",
        // Buckets
        "pumpkin_pulp_bucket",
        "sweetberry_pulp_bucket",
        "vanilla_bucket",
        "melon_pulp_bucket",
        "apple_pulp_bucket",
        "glow_berry_pulp_bucket",
        "orange_pulp_bucket",
        // Crops & block items
        "vanilla_bean",
        "apple_leaves",
        "apple_sapling",
        "orange_leaves",
        "orange_sapling"
    );

    // Solid food items (eat animation) — excludes juices, buckets, and plant items
    private static final List<String> FOOD_ITEMS = List.of(
        "orange",
        "icecream_cone", "icecream", "icecream_chocolate", "icecream_pumpkin", "icecream_sweetberry",
        "icecream_vanilla", "icecream_melon", "icecream_apple", "icecream_glow_berry", "icecream_orange",
        "cupcake_base", "cupcake_chocolate", "cupcake_pumpkin", "cupcake_sweetberry",
        "cupcake_vanilla", "cupcake_melon", "cupcake_apple", "cupcake_glow_berry", "cupcake_orange",
        "yogurt", "yogurt_pumpkin", "yogurt_sweetberry", "yogurt_vanilla",
        "yogurt_melon", "yogurt_apple", "yogurt_glow_berry"
    );

    // Juice items (drink animation) — all return a glass bottle as recipe remainder
    private static final List<String> JUICE_ITEMS = List.of(
        "juice_apple", "juice_glow_berry", "juice_melon", "juice_pumpkin",
        "juice_sweetberry", "juice_orange", "juice_tutti_frutti"
    );

    // All registered pulp fluids — keep in sync with FluidInit.java
    private static final List<Fluid> ALL_PULP_FLUIDS = List.of(
        FluidInit.PUMPKIN_PULP,
        FluidInit.SWEETBERRY_PULP,
        FluidInit.VANILLA,
        FluidInit.MELON_PULP,
        FluidInit.APPLE_PULP,
        FluidInit.GLOW_BERRY_PULP,
        FluidInit.ORANGE_PULP
    );

    @Override
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {
            singleplayer.getClientWorld().waitForChunksRender();

            giveAllModItems(singleplayer.getServer());
            assertNoMissingResources(context);
            assertFoodItemsAreConsumable(singleplayer);
            assertJuicesAreConsumable(singleplayer);
            assertPulpFluidsCanBeStoredInCreateTank(singleplayer);
        }
    }

    private void giveAllModItems(TestServerContext server) {
        for (String item : ALL_ITEMS) {
            server.runCommand("give @p create_new_foods:" + item + " 1");
        }
        server.runCommand("clear @p");
    }

    /**
     * Checks that every mod item has its 1.21+ item definition file (assets/.../items/<name>.json).
     * This is the file Minecraft uses to resolve item rendering — if it is missing, the item
     * will show the pink/black missing-texture placeholder. The old models/item/<name>.json
     * is not checked here since items can reference block models or custom paths directly from
     * the items/ definition, making models/item/ optional.
     */
    private void assertNoMissingResources(ClientGameTestContext context) {
        List<String> missing = context.computeOnClient(client -> {
            var rm = client.getResourceManager();
            List<String> failures = new ArrayList<>();
            for (String itemName : ALL_ITEMS) {
                if (rm.getResource(CreateNewFoods.id("items/" + itemName + ".json")).isEmpty()) {
                    failures.add(itemName + " (missing items/" + itemName + ".json)");
                }
            }
            return failures;
        });

        if (!missing.isEmpty()) {
            throw new AssertionError("Missing resource files for items: " + missing);
        }
    }

    /**
     * For each food item, tests both gates of the consumption pipeline.
     * Gate 1 — ConsumableComponent: in 1.21.4+, food consumption bypasses Item.use() entirely;
     * the interaction system checks DataComponentTypes.CONSUMABLE directly. A missing component
     * means the item can never be eaten regardless of how it is held or used.
     * Gate 2 — finishUsing(): resets hunger to 0, completes the consume action, and asserts
     * the food level increased. Catches zero-nutrition values or a missing FoodComponent.
     */
    private void assertFoodItemsAreConsumable(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerPlayerEntity player = server.getPlayerManager().getPlayerList().getFirst();
            var world = server.getOverworld();
            List<String> fails = new ArrayList<>();

            for (String itemName : FOOD_ITEMS) {
                Item item = Registries.ITEM.get(CreateNewFoods.id(itemName));
                ItemStack stack = new ItemStack(item);

                // Gate 1: item must have ConsumableComponent — the 1.21.4+ prerequisite for eating
                if (stack.get(DataComponentTypes.CONSUMABLE) == null) {
                    fails.add(itemName + " (missing ConsumableComponent)");
                    continue;
                }

                // Gate 2: completing the consume action must actually restore hunger
                player.getHungerManager().setFoodLevel(0);
                int before = player.getHungerManager().getFoodLevel();
                stack.finishUsing(world, player);
                int after = player.getHungerManager().getFoodLevel();
                if (after <= before) {
                    fails.add(itemName + " (hunger did not increase after finishUsing)");
                }
            }
            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Food item consumption failures: " + failures);
        }
    }

    /**
     * Same two gates as assertFoodItemsAreConsumable, plus a third check specific to juices:
     * each juice must declare Items.GLASS_BOTTLE as its recipe remainder, so the bottle is
     * returned when the juice is used as a crafting ingredient.
     */
    private void assertJuicesAreConsumable(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerPlayerEntity player = server.getPlayerManager().getPlayerList().getFirst();
            var world = server.getOverworld();
            List<String> fails = new ArrayList<>();

            for (String itemName : JUICE_ITEMS) {
                Item item = Registries.ITEM.get(CreateNewFoods.id(itemName));
                ItemStack stack = new ItemStack(item);

                // Gate 1: must have ConsumableComponent
                if (stack.get(DataComponentTypes.CONSUMABLE) == null) {
                    fails.add(itemName + " (missing ConsumableComponent)");
                    continue;
                }

                // Gate 2: finishUsing() must restore hunger
                player.getHungerManager().setFoodLevel(0);
                int before = player.getHungerManager().getFoodLevel();
                stack.finishUsing(world, player);
                int after = player.getHungerManager().getFoodLevel();
                if (after <= before) {
                    fails.add(itemName + " (hunger did not increase after finishUsing)");
                }

                // Gate 3: must declare glass bottle as recipe remainder (returned in crafting)
                ItemStack remainder = item.getRecipeRemainder();
                if (remainder.isEmpty() || !remainder.isOf(Items.GLASS_BOTTLE)) {
                    fails.add(itemName + " (recipe remainder is " + remainder + ", expected glass_bottle)");
                }
            }
            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Juice consumption failures: " + failures);
        }
    }

    /**
     * For each pulp fluid, places a fresh create:fluid_tank in the world, inserts one bucket
     * of the fluid via Fabric's FluidStorage API, then reads the storage back and asserts:
     * - the tank accepted the fluid (inserted > 0)
     * - the tank reports the correct FluidVariant after insertion
     */
    private void assertPulpFluidsCanBeStoredInCreateTank(TestSingleplayerContext singleplayer) {
        List<String> failures = singleplayer.getServer().computeOnServer(server -> {
            ServerWorld world = server.getOverworld();
            Block tankBlock = Registries.BLOCK.get(Identifier.of("create", "fluid_tank"));
            // Y=200 is safely above any generated terrain
            BlockPos pos = new BlockPos(0, 200, 0);
            List<String> fails = new ArrayList<>();

            for (Fluid fluid : ALL_PULP_FLUIDS) {
                String fluidName = Registries.FLUID.getId(fluid).toString();

                // Clear to AIR first so Minecraft destroys the block entity, then place fresh tank
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.setBlockState(pos, tankBlock.getDefaultState());

                var storage = FluidStorage.SIDED.find(world, pos, Direction.UP);
                if (storage == null) {
                    fails.add("create:fluid_tank does not expose FluidStorage — test cannot continue");
                    break;
                }

                // Insert exactly one bucket worth of fluid
                FluidVariant variant = FluidVariant.of(fluid);
                long inserted;
                try (Transaction tx = Transaction.openOuter()) {
                    inserted = storage.insert(variant, FluidConstants.BUCKET, tx);
                    tx.commit();
                }
                if (inserted == 0) {
                    fails.add(fluidName + ": tank refused insertion (inserted 0 droplets)");
                    continue;
                }

                // Read back and verify the stored fluid matches what was inserted
                boolean found = false;
                var readStorage = FluidStorage.SIDED.find(world, pos, Direction.UP);
                if (readStorage == null) {
                    fails.add(fluidName + ": tank lost FluidStorage after insertion");
                    continue;
                }
                for (var view : readStorage) {
                    if (!view.isResourceBlank() && view.getResource().isOf(fluid)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    fails.add(fluidName + ": not readable from tank after insertion");
                }
            }

            // Clean up
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return fails;
        });

        if (!failures.isEmpty()) {
            throw new AssertionError("Create tank fluid storage failures: " + failures);
        }
    }
}