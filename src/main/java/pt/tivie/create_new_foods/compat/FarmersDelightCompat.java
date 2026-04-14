package pt.tivie.create_new_foods.compat;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.item.Item;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import pt.tivie.create_new_foods.item.food.FlavoredFood;

public class FarmersDelightCompat {

    public static void load() {
        if (!FabricLoader.getInstance().isModLoaded("farmersdelight")) return;

        DefaultItemComponentEvents.MODIFY.register(context -> {
            apply(context, "melon_juice", FlavoredFood.MELON);
        });
    }

    private static void apply(DefaultItemComponentEvents.ModifyContext context,
                               String itemName, net.minecraft.entity.effect.StatusEffectInstance effect) {
        Identifier id = Identifier.of("farmersdelight", itemName);
        if (!Registries.ITEM.containsId(id)) return;

        Item item = Registries.ITEM.get(id);
        ConsumableComponent consumable = ConsumableComponents.drink()
                .consumeEffect(new ApplyEffectsConsumeEffect(effect, 1.0f))
                .build();

        context.modify(item, builder -> builder.add(DataComponentTypes.CONSUMABLE, consumable));
    }
}