package me.birb.resonantengineering.network;

import me.birb.resonantengineering.network.energynode.EnergyAcceptor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EnergyConduit extends EnergyAcceptor {

    EnergyNetwork getNetwork(World world, BlockPos pos);

}
