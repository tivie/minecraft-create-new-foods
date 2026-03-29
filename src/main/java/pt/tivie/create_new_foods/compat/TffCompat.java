package pt.tivie.create_new_foods.compat;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class TffCompat {

    public static void load() {
        if (!FabricLoader.getInstance().isModLoaded("travel_friendly_food")) return;

        DefaultItemComponentEvents.MODIFY.register(context -> {
            // Ice creams
            apply(context, "vanilla_ice_cream",
                    new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 1));
            apply(context, "chocolate_ice_cream",
                    new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 12 * 20, 1));
            apply(context, "sweet_berry_ice_cream",
                    new StatusEffectInstance(StatusEffects.REGENERATION, 12 * 20, 1));
            // Cupcakes
            apply(context, "vanilla_cupcake",
                    new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12 * 20, 1));
            apply(context, "chocolate_cupcake",
                    new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 12 * 20, 1));
            apply(context, "sweet_berry_cupcake",
                    new StatusEffectInstance(StatusEffects.REGENERATION, 12 * 20, 1));
        });
    }

    private static void apply(DefaultItemComponentEvents.ModifyContext context,
                               String itemName, StatusEffectInstance effect) {
        Identifier id = Identifier.of("travel_friendly_food", itemName);
        if (!Registries.ITEM.containsId(id)) return;

        Item item = Registries.ITEM.get(id);
        ConsumableComponent consumable = ConsumableComponents.food()
                .consumeEffect(new ApplyEffectsConsumeEffect(effect, 1.0f))
                .build();

        context.modify(item, builder -> builder.add(DataComponentTypes.CONSUMABLE, consumable));
    }
}
