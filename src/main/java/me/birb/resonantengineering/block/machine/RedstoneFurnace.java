package me.birb.resonantengineering.block.machine;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class RedstoneFurnace extends Machine implements BlockEntityProvider {

    public RedstoneFurnace(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new MachineBlockEntity();
    }
}
