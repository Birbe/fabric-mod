package me.birb.thermalexpansion.network;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

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

    public DiscoveredNetwork recurseDiscoverNetwork(List<ConduitState> shared) {
        return recurseDiscoverNetwork(shared, new ArrayList<>());
    }

    /**
     * @param shared A list of conduits to know that if we see them as a peer somewhere down the line, to merge the networks between them
     * @return
     */
    public DiscoveredNetwork recurseDiscoverNetwork(List<ConduitState> shared, ArrayList<BlockPos> visited) {
        EnergyNetwork network = new EnergyNetwork();
        NodeList nodeList = new NodeList();
        ArrayList<ConduitState> seenShared = new ArrayList<>();

        recurseDiscoverNetwork(world, pos, shared, seenShared, visited, nodeList);

        NetworkManager.addNetwork(network);

        for(int i=0;i<nodeList.index;i++) {
            BlockPos p = nodeList.nodes[i];
            if(p.getX() == pos.getX() && p.getY() == pos.getY() && p.getZ() == pos.getZ()) continue;

            Block node = world.getBlockState( nodeList.nodes[i]).getBlock();

            if(node instanceof EnergyConduit) {
                ((EnergyConduit) node).setNetwork(world, nodeList.nodes[i], network);
            }

            if(node instanceof EnergyConsumer) network.addConsumer((EnergyConsumer)node);
            if(node instanceof EnergyProducer) network.addProducer((EnergyProducer)node);
        }
        return new DiscoveredNetwork(network, seenShared);
    }

    public DiscoveredNetwork recurseDiscoverNetwork(World world, BlockPos pos, List<ConduitState> shared, List<ConduitState> seenShared) {
        ArrayList<BlockPos> visited = new ArrayList<>();
        NodeList nodeList = new NodeList();
        EnergyNetwork network = new EnergyNetwork();

        recurseDiscoverNetwork(world, pos, shared, seenShared, visited, nodeList);

        NetworkManager.addNetwork(network);

        for(int i=0;i<nodeList.index;i++) {
            BlockPos p = nodeList.nodes[i];
            if(p.getX() == pos.getX() && p.getY() == pos.getY() && p.getZ() == pos.getZ()) continue;

            Block node = world.getBlockState( nodeList.nodes[i]).getBlock();

            if(node instanceof EnergyConduit) {
                EnergyConduit cond = ((EnergyConduit) node);
                if(cond.getNetwork(world, nodeList.nodes[i]) != null) {
                    NetworkManager.getNetworks().remove(cond.getNetwork(world, nodeList.nodes[i]));
                }
                cond.setNetwork(world, nodeList.nodes[i], network);
            }

            if(node instanceof EnergyConsumer) network.addConsumer((EnergyConsumer)node);
            if(node instanceof EnergyProducer) network.addProducer((EnergyProducer)node);
        }
        return new DiscoveredNetwork(network, seenShared);
    }

    public void recurseDiscoverNetwork(World world, BlockPos pos, List<ConduitState> shared, List<ConduitState> seenShared, ArrayList<BlockPos> visited, NodeList state) {
        ArrayList<BlockPos> peers = getPeers(pos);

        for(BlockPos peer : peers) {
            if(!visited.contains(peer)) { //We haven't dealt with this peer yet
                state.nodes[state.index] = peer;
                state.index++;

                for(ConduitState conduit : shared) {
                    if(conduit.pos == pos) {
                        seenShared.add(conduit);
                    }
                }

                visited.add(peer);

                recurseDiscoverNetwork(world, peer, shared, seenShared, visited, state);
            }
        }
    }

    public static class NodeList {

        public BlockPos[] nodes = new BlockPos[100000];
        public int index = 0;

    }

    public static class DiscoveredNetwork {

        public EnergyNetwork network;
        public List<ConduitState> seenShared;

        public DiscoveredNetwork(EnergyNetwork network, List<ConduitState> seenShared) {
            this.network = network;
            this.seenShared = seenShared;
        }

    }

}
