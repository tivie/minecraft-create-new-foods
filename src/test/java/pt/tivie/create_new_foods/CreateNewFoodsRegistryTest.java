package pt.tivie.create_new_foods;

import net.minecraft.SharedConstants;
import net.minecraft.Bootstrap;
import net.minecraft.item.ItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pt.tivie.create_new_foods.init.ItemInit;

class CreateNewFoodsRegistryTest {

    @BeforeAll
    static void bootstrapMinecraft() {
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }

    @Test
    void orangeStackUsesTheRegisteredItem() {
        ItemStack stack = new ItemStack(ItemInit.ORANGE);
        Assertions.assertTrue(stack.isOf(ItemInit.ORANGE));
        Assertions.assertEquals(1, stack.getCount());
    }
}
