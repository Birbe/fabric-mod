package me.birb.thermalexpansion.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public class Evaporator extends Machine {

    public Evaporator(Settings settings) {
        super(settings);
    }

//    @Override
//    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
//        //Direction facing = Direction.fromRotation(placer.getHeadYaw()).getOpposite();
//        //world.setBlockState(pos, world.getBlockState(pos).with(direction, facing));
//    }
}
