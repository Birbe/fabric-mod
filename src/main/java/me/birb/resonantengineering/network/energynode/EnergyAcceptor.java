package me.birb.resonantengineering.network.energynode;

import me.birb.resonantengineering.network.ConduitState;
import me.birb.resonantengineering.network.EnergyConduit;
import me.birb.resonantengineering.network.EnergyNetwork;
import me.birb.resonantengineering.network.NetworkManager;
import me.birb.resonantengineering.network.energynode.state.EnergyNodeState;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public interface EnergyAcceptor {

    default EnergyNetwork joinNetwork(BlockPos pos, World world) {
        List<ConduitState> peers = getConduitPeers(pos, world);
        List<PeerState> discovered = recurseDiscoverNetwork(world, pos, new ArrayList<>(), new ArrayList<>());
        EnergyNetwork newnet = null;
        if(!peers.isEmpty()) {
            for(PeerState peer : discovered) {
                if(peer.getNetwork() != null) newnet = peer.getNetwork();
                break;
            }
            if(newnet == null) { //This shouldn't happen
                newnet = new EnergyNetwork();
                NetworkManager.registerNetwork(newnet);
            }
            for(int i=1;i<peers.size();i++) NetworkManager.removeNetwork(peers.get(i).getNetwork());
            newnet.setNodes(new ArrayList<>()); //Clear the nodes, they will be discovered
            for(PeerState discoveredPeer : discovered) {
                if(discoveredPeer instanceof EnergyNodeState) {
                    newnet.addNode((EnergyNodeState<?>) discoveredPeer);
                }
                discoveredPeer.setNetwork(newnet);
            }
        } else {
            newnet = new EnergyNetwork();
            NetworkManager.registerNetwork(newnet);
            setNetwork(pos, world, newnet);
        }
        return newnet;
    }

    default List<PeerState> recurseDiscoverNetwork(World world, BlockPos pos, ArrayList<BlockPos> ignore, ArrayList<PeerState> peersOut) {
        List<PeerState> peers = getPeers(pos, world);
        for(PeerState peer : peers) {
            if(!ignore.contains(peer.getPos())) {
                ignore.add(peer.getPos());
                peersOut.add(peer);
                if(peer instanceof ConduitState) recurseDiscoverNetwork(world, peer.getPos(), ignore, peersOut);
            }
        }
        return peersOut;
    }

    default List<PeerState> getPeers(BlockPos pos, World world) {
        ArrayList<PeerState> peers = new ArrayList<>();

        for(Direction direction : Direction.values()) {
            BlockPos peerPos = pos.offset(direction);
            Block block = world.getBlockState(pos.offset(direction)).getBlock();

            if(block instanceof EnergyConduit) {
                peers.add(new ConduitState(peerPos, world, (EnergyConduit)block));
            } else if(block instanceof EnergyNode) {
                peers.add(EnergyNodeState.getCastedInstance(world, peerPos, (EnergyNode)block));
            }
        }

        return peers;
    }

    default List<ConduitState> getConduitPeers(BlockPos pos, World world) {
        ArrayList<ConduitState> conduits = new ArrayList<>();
        for(Direction direction : Direction.values()) {
            Block block = world.getBlockState(pos.offset(direction)).getBlock();
            if(block instanceof EnergyConduit) {
                conduits.add(new ConduitState(pos.offset(direction), world, (EnergyConduit) block));
            }
        }
        return conduits;
    }

    void setNetwork(BlockPos pos, World world, EnergyNetwork network);

}
