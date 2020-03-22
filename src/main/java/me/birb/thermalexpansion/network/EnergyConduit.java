package me.birb.thermalexpansion.network;

import me.birb.thermalexpansion.network.energynode.EnergyAcceptor;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public interface EnergyConduit extends EnergyAcceptor {

    EnergyNetwork getNetwork(World world, BlockPos pos);

}
