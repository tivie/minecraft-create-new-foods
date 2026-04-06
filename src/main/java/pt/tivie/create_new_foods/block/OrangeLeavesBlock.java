package pt.tivie.create_new_foods.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.item.Item;
import net.minecraft.state.property.BooleanProperty;
import pt.tivie.create_new_foods.init.ItemInit;

public class OrangeLeavesBlock extends HarvestableLeavesBlock {

    public static final MapCodec<OrangeLeavesBlock> CODEC = createCodec(OrangeLeavesBlock::new);
    public static final BooleanProperty HAS_ORANGES = BooleanProperty.of("has_oranges");

    public OrangeLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<OrangeLeavesBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BooleanProperty getHasFruitProperty() {
        return HAS_ORANGES;
    }

    @Override
    public Item getFruitItem() {
        return ItemInit.ORANGE;
    }
}