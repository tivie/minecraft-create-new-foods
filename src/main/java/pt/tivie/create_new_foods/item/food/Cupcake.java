package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;

public class Cupcake {

    public static Item.Settings getCupcakeVanilla()    { return FlavoredFood.food(base(), FlavoredFood.VANILLA); }
    public static Item.Settings getCupcakeChocolate()  { return FlavoredFood.food(base(), FlavoredFood.CHOCOLATE); }
    public static Item.Settings getCupcakePumpkin()    { return FlavoredFood.food(base(), FlavoredFood.PUMPKIN); }
    public static Item.Settings getCupcakeSweetberry() { return FlavoredFood.food(base(), FlavoredFood.SWEET_BERRY); }
    public static Item.Settings getCupcakeMelon()      { return FlavoredFood.food(base(), FlavoredFood.MELON); }
    public static Item.Settings getCupcakeApple()      { return FlavoredFood.food(base(), FlavoredFood.APPLE); }
    public static Item.Settings getCupcakeGlowBerry()  { return FlavoredFood.food(base(), FlavoredFood.GLOW_BERRY); }

    public static Item.Settings getCupcakeBase() {
        FoodComponent food = new FoodComponent.Builder()
                .nutrition(2)
                .saturationModifier(0.1F)
                .build();
        return new Item.Settings().food(food);
    }

    private static FoodComponent base() {
        return new FoodComponent.Builder()
                .nutrition(5)
                .saturationModifier(0.6F)
                .alwaysEdible()
                .build();
    }
}