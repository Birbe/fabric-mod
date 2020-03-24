package me.birb.resonantengineering.network.energynode;

import me.birb.resonantengineering.network.EnergyNetwork;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EnergyNode extends EnergyAcceptor {

    EnergyNetwork getNetwork(World world, BlockPos pos);

    void setNetwork(BlockPos pos, World world, EnergyNetwork network);

    void energyTick();

}
