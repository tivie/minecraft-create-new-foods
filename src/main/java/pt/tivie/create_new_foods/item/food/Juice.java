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

    private static FoodComponent.Builder createJuice() {
        return new FoodComponent.Builder()
                .nutrition(2)
                .saturationModifier(0.3F)
                .alwaysEdible();
    }

    public static Item.Settings getJuiceApple() {
        FoodComponent foodComponent = createJuice().build();
        ConsumableComponent consumableComponent = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.STRENGTH, 12 * 20, 0),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent).recipeRemainder(Items.GLASS_BOTTLE);
    }

    public static Item.Settings getJuiceGlowBerry() {
        FoodComponent foodComponent = createJuice().build();
        ConsumableComponent consumableComponent = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.GLOWING, 12 * 20, 0),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent).recipeRemainder(Items.GLASS_BOTTLE);
    }

    public static Item.Settings getJuiceMelon() {
        FoodComponent foodComponent = createJuice().build();
        ConsumableComponent consumableComponent = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.ABSORPTION, 12 * 20, 0),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent).recipeRemainder(Items.GLASS_BOTTLE);
    }

    public static Item.Settings getJuicePumpkin() {
        FoodComponent foodComponent = createJuice().build();
        ConsumableComponent consumableComponent = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.SPEED, 12 * 20, 0),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent).recipeRemainder(Items.GLASS_BOTTLE);
    }

    public static Item.Settings getJuiceSweetberry() {
        FoodComponent foodComponent = createJuice().build();
        ConsumableComponent consumableComponent = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.REGENERATION, 12 * 20, 0),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent).recipeRemainder(Items.GLASS_BOTTLE);
    }

    public static Item.Settings getJuiceTuttiFrutti() {
        FoodComponent foodComponent = createJuice().build();
        ConsumableComponent consumableComponent = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.STRENGTH,    60 * 20, 0), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.GLOWING,     60 * 20, 0), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,  60 * 20, 0), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.SPEED,       60 * 20, 0), 1.0f))
                .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60 * 20, 0), 1.0f))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent).recipeRemainder(Items.GLASS_BOTTLE);
    }
}
