package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class Cupcake {

    public static Item.Settings getCupcakeBase() {

        FoodComponent foodComponent = new FoodComponent.Builder()
                .nutrition(2)
                .saturationModifier(0.1F)
                .build();

        return new Item.Settings().food(foodComponent);
    }

    // used as base for all cupcakes
    private static FoodComponent.Builder createCupcake() {
        return new FoodComponent.Builder()
                .nutrition(5)
                .saturationModifier(0.6F)
                .alwaysEdible();
    }

    public static Item.Settings getCupcakeChocolate() {
        FoodComponent foodComponent = createCupcake().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getCupcakePumpkin() {
        FoodComponent foodComponent = createCupcake().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.SPEED, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getCupcakeSweetberry() {
        FoodComponent foodComponent = createCupcake().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.REGENERATION, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getCupcakeVanilla() {
        FoodComponent foodComponent = createCupcake().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getCupcakeMelon() {
        FoodComponent foodComponent = createCupcake().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.ABSORPTION, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getCupcakeApple() {
        FoodComponent foodComponent = createCupcake().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.STRENGTH, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

    public static Item.Settings getCupcakeGlowBerry() {
        FoodComponent foodComponent = createCupcake().build();
        ConsumableComponent consumableComponent = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(
                        new StatusEffectInstance(StatusEffects.NIGHT_VISION, 12 * 20, 1),
                        1.0f
                ))
                .build();
        return new Item.Settings().food(foodComponent, consumableComponent);
    }

}
