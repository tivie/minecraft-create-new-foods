package pt.tivie.create_new_foods.item.food;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class FlavoredFood {

    // Flavor → effect mapping; single source of truth for all flavored foods
    public static final StatusEffectInstance VANILLA     = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 1);
    public static final StatusEffectInstance CHOCOLATE   = new StatusEffectInstance(StatusEffects.HASTE,            12 * 20, 1);
    public static final StatusEffectInstance PUMPKIN     = new StatusEffectInstance(StatusEffects.SPEED,           12 * 20, 1);
    public static final StatusEffectInstance SWEET_BERRY = new StatusEffectInstance(StatusEffects.REGENERATION,    12 * 20, 1);
    public static final StatusEffectInstance MELON       = new StatusEffectInstance(StatusEffects.ABSORPTION,      12 * 20, 1);
    public static final StatusEffectInstance APPLE       = new StatusEffectInstance(StatusEffects.STRENGTH,        12 * 20, 1);
    public static final StatusEffectInstance GLOW_BERRY  = new StatusEffectInstance(StatusEffects.GLOWING,         12 * 20, 1);
    public static final StatusEffectInstance ORANGE      = new StatusEffectInstance(StatusEffects.HEALTH_BOOST,     12 * 20, 1);

    public static Item.Settings food(FoodComponent food, StatusEffectInstance effect) {
        ConsumableComponent consumable = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(effect, 1.0f))
                .build();
        return new Item.Settings().food(food, consumable);
    }

    public static Item.Settings drink(FoodComponent food, StatusEffectInstance effect) {
        ConsumableComponent consumable = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(effect, 1.0f))
                .build();
        return new Item.Settings().food(food, consumable);
    }

    private FlavoredFood() {}
}