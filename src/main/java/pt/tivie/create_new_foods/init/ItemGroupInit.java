package pt.tivie.create_new_foods.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import pt.tivie.create_new_foods.CreateNewFoods;

public class ItemGroupInit {
    public static final RegistryKey<ItemGroup> create_new_foods_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, CreateNewFoods.id("create_new_foods_group"));
    public static final ItemGroup create_new_foods_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemInit.ICECREAM))
            .displayName(Text.translatable("itemGroup.create_new_foods.group"))
            .entries((displayContext, entries) -> {
                entries.add(ItemInit.VANILLA_BEAN);
                entries.add(ItemInit.APPLE_SAPLING);
                entries.add(ItemInit.APPLE_LEAVES);
                entries.add(ItemInit.ORANGE);
                entries.add(ItemInit.ORANGE_SAPLING);
                entries.add(ItemInit.ORANGE_LEAVES);
                entries.add(ItemInit.PUMPKIN_PULP_BUCKET);
                entries.add(ItemInit.SWEETBERRY_PULP_BUCKET);
                entries.add(ItemInit.VANILLA_BUCKET);
                entries.add(ItemInit.MELON_PULP_BUCKET);
                entries.add(ItemInit.APPLE_PULP_BUCKET);
                entries.add(ItemInit.GLOW_BERRY_PULP_BUCKET);
                entries.add(ItemInit.ORANGE_PULP_BUCKET);
                entries.add(ItemInit.ICECREAM_CONE);
                entries.add(ItemInit.ICECREAM);
                entries.add(ItemInit.ICECREAM_CHOCOLATE);
                entries.add(ItemInit.ICECREAM_SWEETBERRY);
                entries.add(ItemInit.ICECREAM_PUMPKIN);
                entries.add(ItemInit.ICECREAM_VANILLA);
                entries.add(ItemInit.ICECREAM_MELON);
                entries.add(ItemInit.ICECREAM_APPLE);
                entries.add(ItemInit.ICECREAM_GLOW_BERRY);
                entries.add(ItemInit.ICECREAM_ORANGE);
                entries.add(ItemInit.CUPCAKE_BASE);
                entries.add(ItemInit.CUPCAKE_CHOCOLATE);
                entries.add(ItemInit.CUPCAKE_PUMPKIN);
                entries.add(ItemInit.CUPCAKE_SWEETBERRY);
                entries.add(ItemInit.CUPCAKE_VANILLA);
                entries.add(ItemInit.CUPCAKE_MELON);
                entries.add(ItemInit.CUPCAKE_APPLE);
                entries.add(ItemInit.CUPCAKE_GLOW_BERRY);
                entries.add(ItemInit.CUPCAKE_ORANGE);
                entries.add(ItemInit.JUICE_APPLE);
                entries.add(ItemInit.JUICE_GLOW_BERRY);
                entries.add(ItemInit.JUICE_MELON);
                entries.add(ItemInit.JUICE_PUMPKIN);
                entries.add(ItemInit.JUICE_SWEETBERRY);
                entries.add(ItemInit.JUICE_ORANGE);
                entries.add(ItemInit.JUICE_TUTTI_FRUTTI);
                entries.add(ItemInit.YOGURT);
                entries.add(ItemInit.YOGURT_PUMPKIN);
                entries.add(ItemInit.YOGURT_SWEETBERRY);
                entries.add(ItemInit.YOGURT_VANILLA);
                entries.add(ItemInit.YOGURT_MELON);
                entries.add(ItemInit.YOGURT_APPLE);
                entries.add(ItemInit.YOGURT_GLOW_BERRY);
            })
            .build();

    public static void load() {
        Registry.register(Registries.ITEM_GROUP, create_new_foods_GROUP_KEY, create_new_foods_GROUP);
    }
}
