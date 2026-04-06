package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class Juice {

    public static Item.Settings getJuiceApple()      { return juice(FlavoredFood.APPLE); }
    public static Item.Settings getJuiceGlowBerry()  { return juice(FlavoredFood.GLOW_BERRY); }
    public static Item.Settings getJuiceMelon()      { return juice(FlavoredFood.MELON); }
    public static Item.Settings getJuicePumpkin()    { return juice(FlavoredFood.PUMPKIN); }
    public static Item.Settings getJuiceSweetberry() { return juice(FlavoredFood.SWEET_BERRY); }

    // Tutti frutti grants all flavors at once — too many effects for the shared helper
    public static Item.Settings getJuiceTuttiFrutti() {
        ConsumableComponent consumable = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.STRENGTH,     60 * 20, 1), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.GLOWING,      60 * 20, 1), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,   60 * 20, 1), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.SPEED,        60 * 20, 1), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60 * 20, 1), 1.0f))
                .build();
        return new Item.Settings().food(base(), consumable).recipeRemainder(Items.GLASS_BOTTLE);
    }

    private static Item.Settings juice(StatusEffectInstance effect) {
        return FlavoredFood.drink(base(), effect).recipeRemainder(Items.GLASS_BOTTLE);
    }

    private static FoodComponent base() {
        return new FoodComponent.Builder()
                .nutrition(2)
                .saturationModifier(0.3F)
                .alwaysEdible()
                .build();
    }
}
