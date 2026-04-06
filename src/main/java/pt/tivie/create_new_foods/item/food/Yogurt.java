package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class Yogurt {

    public static Item.Settings getYogurtVanilla()    { return FlavoredFood.food(base(), FlavoredFood.VANILLA); }
    public static Item.Settings getYogurtPumpkin()    { return FlavoredFood.food(base(), FlavoredFood.PUMPKIN); }
    public static Item.Settings getYogurtSweetberry() { return FlavoredFood.food(base(), FlavoredFood.SWEET_BERRY); }
    public static Item.Settings getYogurtMelon()      { return FlavoredFood.food(base(), FlavoredFood.MELON); }
    public static Item.Settings getYogurtApple()      { return FlavoredFood.food(base(), FlavoredFood.APPLE); }
    public static Item.Settings getYogurtGlowBerry()  { return FlavoredFood.food(base(), FlavoredFood.GLOW_BERRY); }

    // Plain yogurt: distinct nutrition, shorter duration, lesser amplifier — not a flavored variant
    public static Item.Settings getYogurt() {
        FoodComponent food = new FoodComponent.Builder()
                .nutrition(4)
                .saturationModifier(0.5F)
                .build();
        ConsumableComponent consumable = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.REGENERATION, 8 * 20, 0),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(food, consumable);
    }

    private static FoodComponent base() {
        return new FoodComponent.Builder()
                .nutrition(5)
                .saturationModifier(0.6F)
                .alwaysEdible()
                .build();
    }
}
