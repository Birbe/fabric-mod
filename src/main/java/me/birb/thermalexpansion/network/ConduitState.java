package me.birb.thermalexpansion.network;

import me.birb.thermalexpansion.block.conduit.RedstoneConduit;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static me.birb.thermalexpansion.FabricMod.REDSTONE_CONDUIT;

public class ConduitState {

    private BlockPos pos;
    private EnergyConduit conduit;
    private World world;

    public ConduitState(BlockPos pos, World world, EnergyConduit conduit) {
        this.pos = pos;
        this.conduit = conduit;
        this.world = world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnergyConduit getConduit() {
        return conduit;
    }

    public EnergyNetwork getNetwork() {
        return conduit.getNetwork(world, pos);
    }

    public static long posToLong(BlockPos pos, BlockPos offset) {
        return new BlockPos(pos.getX()-offset.getX(), pos.getY()-offset.getY(), pos.getZ()-offset.getZ()).asLong();
    }

    public static BlockPos posFromLong(long longue, BlockPos offset) {
        BlockPos p = BlockPos.fromLong(longue);
        return new BlockPos(p.getX()+offset.getX(), p.getY()+offset.getY(), p.getZ()+offset.getZ());
    }

    public ArrayList<BlockPos> getPeers(BlockPos pos) {
        ArrayList<BlockPos> peers = new ArrayList<>();
        for(Direction direction : Direction.values()) {
            Block block = world.getBlockState(pos.offset(direction)).getBlock();
            if(block instanceof EnergyConduit || block instanceof EnergyNode) {
                peers.add(pos.offset(direction));
            }
        }
        return peers;
    }

    public ArrayList<BlockPos> recurseDiscoverNetwork(World world, BlockPos pos, ArrayList<BlockPos> ignore, ArrayList<BlockPos> peersOut) {
        ArrayList<BlockPos> peers = getPeers(pos);
        for(BlockPos peer : peers) {
            if(!ignore.contains(peer)) {
                ignore.add(peer);
                peersOut.add(peer);
                recurseDiscoverNetwork(world, peer, ignore, peersOut);
            }
        }
        return peersOut;
    }

    public void recurseSetNetwork(World world, BlockPos pos, ArrayList<Long> visited, EnergyNetwork network) {
        List<BlockPos> peers = getPeers(pos);
        for(BlockPos peer : peers) {
            if(!visited.contains(peer.asLong())) {
                visited.add(peer.asLong());
                ((RedstoneConduit) REDSTONE_CONDUIT).setNetwork(world, peer, network);
                recurseSetNetwork(world, peer, visited, network);
            }
        }
    }

    public static class NodeList {

        public BlockPos[] nodes = new BlockPos[100000];
        public int index = 0;

    }

    public static class DiscoveredNetwork {

        public EnergyNetwork network;
        public List<Long> seenShared;

        public DiscoveredNetwork(EnergyNetwork network, List<Long> seenShared) {
            this.network = network;
            this.seenShared = seenShared;
        }

    }

}
