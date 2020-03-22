package me.birb.thermalexpansion.network;

import me.birb.thermalexpansion.block.conduit.RedstoneConduit;
import me.birb.thermalexpansion.network.energynode.EnergyNode;
import me.birb.thermalexpansion.network.energynode.PeerState;
import me.birb.thermalexpansion.network.energynode.state.EnergyNodeState;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConduitState implements PeerState {

    private BlockPos pos;
    private EnergyConduit conduit;
    private World world;

    public ConduitState(BlockPos pos, World world, EnergyConduit conduit) {
        this.pos = pos;
        this.conduit = conduit;
        this.world = world;
    }

    @Override
    public void setNetwork(EnergyNetwork network) {
        conduit.setNetwork(pos, world, network);
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public World getWorld() {
        return world;
    }

    public EnergyConduit getConduit() {
        return conduit;
    }

    public EnergyNetwork getNetwork() {
        return conduit.getNetwork(world, pos);
    }

    public List<PeerState> recurseDiscoverNetwork(World world, BlockPos pos, ArrayList<BlockPos> ignore, ArrayList<PeerState> peersOut) {
        return conduit.recurseDiscoverNetwork(world, pos, ignore, peersOut);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConduitState that = (ConduitState) o;
        return Objects.equals(pos, that.pos) &&
                Objects.equals(conduit, that.conduit) &&
                Objects.equals(world, that.world) || (
                        pos.getX() == that.getPos().getX() &&
                                pos.getY() == that.getPos().getY() &&
                                pos.getZ() == that.getPos().getZ()
                );
    }

}
