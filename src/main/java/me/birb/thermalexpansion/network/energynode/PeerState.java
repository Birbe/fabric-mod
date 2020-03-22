package me.birb.thermalexpansion.network.energynode;

import me.birb.thermalexpansion.network.EnergyNetwork;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PeerState {

    void setNetwork(EnergyNetwork network);

    BlockPos getPos();

    World getWorld();

     EnergyNetwork getNetwork();
}
