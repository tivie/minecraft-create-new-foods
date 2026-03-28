package pt.tivie.create_new_foods.init;

import com.zurrtum.create.infrastructure.fluids.FlowableFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import pt.tivie.create_new_foods.CreateNewFoods;
import pt.tivie.create_new_foods.item.food.Cupcake;
import pt.tivie.create_new_foods.item.food.IceCream;

import java.util.function.Function;

public class ItemInit {

    public static void load() {}

    public static <T extends Item> T registerItem(String name, Function<Item.Settings, T> itemFactory, Item.Settings settings) {
        Identifier id = CreateNewFoods.id(name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        T item = itemFactory.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    private static BucketItem registerBucketItem(String name, FlowableFluid fluid) {
        BucketItem bucket = registerItem(name, settings -> new BucketItem(fluid, settings),
            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
        fluid.getEntry().bucket = bucket;
        return bucket;
    }

    // Buckets
    public static final BucketItem PUMPKIN_PULP_BUCKET = registerBucketItem("pumpkin_pulp_bucket", FluidInit.PUMPKIN_PULP);
    public static final BucketItem SWEETBERRY_PULP_BUCKET = registerBucketItem("sweetberry_pulp_bucket", FluidInit.SWEETBERRY_PULP);
    public static final BucketItem VANILLA_BUCKET = registerBucketItem("vanilla_bucket", FluidInit.VANILLA);
    public static final BucketItem MELON_PULP_BUCKET = registerBucketItem("melon_pulp_bucket", FluidInit.MELON_PULP);
    public static final BucketItem APPLE_PULP_BUCKET = registerBucketItem("apple_pulp_bucket", FluidInit.APPLE_PULP);
    public static final BucketItem GLOW_BERRY_PULP_BUCKET = registerBucketItem("glow_berry_pulp_bucket", FluidInit.GLOW_BERRY_PULP);

    // Crops
    public static final Item VANILLA_BEAN = registerItem("vanilla_bean",
            settings -> new BlockItem(BlockInit.VANILLA_BEAN, settings),
            new Item.Settings());

    // Items
    public static final Item ICECREAM_CONE = registerItem("icecream_cone", Item::new, IceCream.getIceCreamCone());
    public static final Item ICECREAM = registerItem("icecream", Item::new, IceCream.getIceCream());
    public static final Item ICECREAM_CHOCOLATE = registerItem("icecream_chocolate", Item::new, IceCream.getIceCreamChocolate());
    public static final Item ICECREAM_PUMPKIN = registerItem("icecream_pumpkin", Item::new, IceCream.getIceCreamPumpkin());
    public static final Item ICECREAM_SWEETBERRY = registerItem("icecream_sweetberry", Item::new, IceCream.getIceCreamSweetBerry());
    public static final Item ICECREAM_VANILLA = registerItem("icecream_vanilla", Item::new, IceCream.getIceCreamVanilla());
    public static final Item ICECREAM_MELON = registerItem("icecream_melon", Item::new, IceCream.getIceCreamMelon());
    public static final Item ICECREAM_APPLE = registerItem("icecream_apple", Item::new, IceCream.getIceCreamApple());

    // Baking
    public static final Item CUPCAKE_BASE = registerItem("cupcake_base", Item::new, Cupcake.getCupcakeBase());
    public static final Item CUPCAKE_CHOCOLATE = registerItem("cupcake_chocolate", Item::new, Cupcake.getCupcakeChocolate());
    public static final Item CUPCAKE_PUMPKIN = registerItem("cupcake_pumpkin", Item::new, Cupcake.getCupcakePumpkin());
    public static final Item CUPCAKE_SWEETBERRY = registerItem("cupcake_sweetberry", Item::new, Cupcake.getCupcakeSweetberry());
    public static final Item CUPCAKE_VANILLA = registerItem("cupcake_vanilla", Item::new, Cupcake.getCupcakeVanilla());
    public static final Item CUPCAKE_MELON = registerItem("cupcake_melon", Item::new, Cupcake.getCupcakeMelon());
    public static final Item CUPCAKE_APPLE = registerItem("cupcake_apple", Item::new, Cupcake.getCupcakeApple());
    public static final Item CUPCAKE_GLOW_BERRY = registerItem("cupcake_glow_berry", Item::new, Cupcake.getCupcakeGlowBerry());

    public static final Item ICECREAM_GLOW_BERRY = registerItem("icecream_glow_berry", Item::new, IceCream.getIceCreamGlowBerry());
}
