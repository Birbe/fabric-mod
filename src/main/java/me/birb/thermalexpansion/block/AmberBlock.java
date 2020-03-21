package me.birb.thermalexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class AmberBlock extends Block {

    public AmberBlock(Settings settings) {
        super(settings);
    }

    @Override
    public float getHardness(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }
}
