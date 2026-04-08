package pt.tivie.create_new_foods;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;

public class CreateNewFoodsClientGameTest implements FabricClientGameTest {

    @Override
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {
            singleplayer.getClientWorld().waitForChunksRender();

            ResourceGameTests.giveAndClearAllItems(singleplayer.getServer());
            ResourceGameTests.assertNoMissingResources(context);
            FoodGameTests.assertFoodItemsAreConsumable(singleplayer);
            FoodGameTests.assertJuicesAreConsumable(singleplayer);
            FluidGameTests.assertPulpFluidsCanBeStoredInCreateTank(singleplayer);
            PlantGameTests.assertSaplingsGrow(singleplayer);
            PlantGameTests.assertLeavesFruitWithBonemeal(singleplayer);
            PlantGameTests.assertLeavesDropFruitOnRightClick(singleplayer);
            PlantGameTests.assertMechanicalHarvesterHarvestsFruitLeaves(singleplayer);
        }
    }
}
