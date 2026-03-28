package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class IceCream {

    // icecream_cone
    public static Item.Settings getIceCreamCone() {

        FoodComponent foodComponent = new FoodComponent.Builder()
                .nutrition(2)
                .saturationModifier(0.1F)
                .build();

        return new Item.Settings().food(foodComponent);
    }

    // used as base for all ice creams
    private static FoodComponent.Builder createIceCream() {
        return new FoodComponent.Builder()
                .nutrition(3)
                .saturationModifier(0.4F)
                .alwaysEdible();
    }

    public static Item.Settings getIceCream() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getIceCreamChocolate() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getIceCreamPumpkin() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.SPEED, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getIceCreamSweetBerry() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.REGENERATION, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getIceCreamVanilla() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getIceCreamMelon() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.ABSORPTION, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getIceCreamApple() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.STRENGTH, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getIceCreamGlowBerry() {
        FoodComponent foodComponent = createIceCream().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                // duration is in ticks; 20 ticks = 1 second
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.GLOWING, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }


}
