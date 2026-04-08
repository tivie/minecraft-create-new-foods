package pt.tivie.create_new_foods;

import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks that every registered mod item has its 1.21+ item definition file
 * (assets/.../items/<name>.json). Also provides the shared item-giving setup step
 * used before resource checks (exercises the registration pipeline by giving then
 * immediately clearing all items).
 */
class ResourceGameTests {

    // One entry per registered item — keep in sync with ItemInit.java
    static final List<String> ALL_ITEMS = List.of(
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

    static void giveAndClearAllItems(TestServerContext server) {
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
    static void assertNoMissingResources(ClientGameTestContext context) {
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
}