package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class Yogurt {

    public static Item.Settings getYogurt() {
        FoodComponent foodComponent = new FoodComponent.Builder()
                .nutrition(4)
                .saturationModifier(0.5F)
                .build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.REGENERATION, 8 * 20, 0),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    private static FoodComponent.Builder createFlavoredYogurt() {
        return new FoodComponent.Builder()
                .nutrition(5)
                .saturationModifier(0.6F)
                .alwaysEdible();
    }

    public static Item.Settings getYogurtPumpkin() {
        FoodComponent foodComponent = createFlavoredYogurt().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.SPEED, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getYogurtSweetberry() {
        FoodComponent foodComponent = createFlavoredYogurt().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.REGENERATION, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getYogurtVanilla() {
        FoodComponent foodComponent = createFlavoredYogurt().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getYogurtMelon() {
        FoodComponent foodComponent = createFlavoredYogurt().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.ABSORPTION, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getYogurtApple() {
        FoodComponent foodComponent = createFlavoredYogurt().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.STRENGTH, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getYogurtGlowBerry() {
        FoodComponent foodComponent = createFlavoredYogurt().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.GLOWING, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }
}