package com.r3944realms.whimsy.content.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.jetbrains.annotations.NotNull;

public class TestBlock extends HorizontalDirectionalBlock {
    public TestBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(TestBlock::new);
    }
}
