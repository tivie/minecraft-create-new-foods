package pt.tivie.create_new_foods.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;

public class AppleLeavesBlock extends HarvestableLeavesBlock {

    public static final MapCodec<AppleLeavesBlock> CODEC = createCodec(AppleLeavesBlock::new);
    public static final BooleanProperty HAS_APPLES = BooleanProperty.of("has_apples");

    public AppleLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<AppleLeavesBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BooleanProperty getHasFruitProperty() {
        return HAS_APPLES;
    }

    @Override
    public Item getFruitItem() {
        return Items.APPLE;
    }
}