package me.birb.resonantengineering.network.energynode;

import me.birb.resonantengineering.network.EnergyNetwork;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PeerState {

    void setNetwork(EnergyNetwork network);

    BlockPos getPos();

    World getWorld();

     EnergyNetwork getNetwork();
}
