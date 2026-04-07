package pt.tivie.create_new_foods;

import java.lang.reflect.Method;

import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.registry.Registries;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;

public class CreateNewFoodsGameTest implements CustomTestMethodInvoker {

    @GameTest
    public void orangeItemIsRegistered(TestContext context) {
        context.assertTrue(Registries.ITEM.containsId(CreateNewFoods.id("orange")), Text.literal("orange item should be registered"));
        context.complete();
    }

    @Override
    public void invokeTestMethod(TestContext context, Method method) throws ReflectiveOperationException {
        method.invoke(this, context);
    }
}
