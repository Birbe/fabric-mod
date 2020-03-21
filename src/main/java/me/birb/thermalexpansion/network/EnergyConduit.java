package me.birb.thermalexpansion.network;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public interface EnergyConduit {

    EnergyNetwork getNetwork(World world, BlockPos pos);

    void setNetwork(World world, BlockPos pos, EnergyNetwork network);

    default List<ConduitState> getPeers(World world, BlockPos pos) {
        ArrayList<ConduitState> peers = new ArrayList<>();
        for(Direction direction : Direction.values()) {
            BlockPos peerPos = pos.offset(direction);
            Block peerBlock = getNetwork(world, pos).getWorld().getBlockState(peerPos).getBlock();
            if(peerBlock instanceof EnergyConduit) {
                peers.add(new ConduitState(peerPos,  world, (EnergyConduit) peerBlock));

            }
        }

        return peers;
    }

}
