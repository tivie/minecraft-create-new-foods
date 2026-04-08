package pt.tivie.create_new_foods;

import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifies that all solid food and juice items are properly consumable.
 */
class FoodGameTests {

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

    /**
     * For each food item, tests both gates of the consumption pipeline.
     * Gate 1 — ConsumableComponent: in 1.21.4+, food consumption bypasses Item.use() entirely;
     * the interaction system checks DataComponentTypes.CONSUMABLE directly. A missing component
     * means the item can never be eaten regardless of how it is held or used.
     * Gate 2 — finishUsing(): resets hunger to 0, completes the consume action, and asserts
     * the food level increased. Catches zero-nutrition values or a missing FoodComponent.
     */
    static void assertFoodItemsAreConsumable(TestSingleplayerContext singleplayer) {
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
    static void assertJuicesAreConsumable(TestSingleplayerContext singleplayer) {
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
}