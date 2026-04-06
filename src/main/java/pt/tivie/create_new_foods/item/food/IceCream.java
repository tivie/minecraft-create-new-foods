package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;

public class IceCream {

    public static Item.Settings getIceCreamVanilla()    { return FlavoredFood.food(base(), FlavoredFood.VANILLA); }
    public static Item.Settings getIceCreamChocolate()  { return FlavoredFood.food(base(), FlavoredFood.CHOCOLATE); }
    public static Item.Settings getIceCreamPumpkin()    { return FlavoredFood.food(base(), FlavoredFood.PUMPKIN); }
    public static Item.Settings getIceCreamSweetBerry() { return FlavoredFood.food(base(), FlavoredFood.SWEET_BERRY); }
    public static Item.Settings getIceCreamMelon()      { return FlavoredFood.food(base(), FlavoredFood.MELON); }
    public static Item.Settings getIceCreamApple()      { return FlavoredFood.food(base(), FlavoredFood.APPLE); }
    public static Item.Settings getIceCreamGlowBerry()  { return FlavoredFood.food(base(), FlavoredFood.GLOW_BERRY); }
    public static Item.Settings getIceCreamOrange()     { return FlavoredFood.food(base(), FlavoredFood.ORANGE); }

    public static Item.Settings getIceCreamCone() {
        FoodComponent food = new FoodComponent.Builder()
                .nutrition(2)
                .saturationModifier(0.1F)
                .build();
        return new Item.Settings().food(food);
    }

    // Plain ice cream: same effect as vanilla but lesser amplifier (level I vs level II)
    public static Item.Settings getIceCream() {
        return FlavoredFood.food(base(), new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 0));
    }

    private static FoodComponent base() {
        return new FoodComponent.Builder()
                .nutrition(3)
                .saturationModifier(0.4F)
                .alwaysEdible()
                .build();
    }
}
