package me.birb.resonantengineering.network.energynode.state;

import me.birb.resonantengineering.network.EnergyNetwork;
import me.birb.resonantengineering.network.energynode.EnergyConsumer;
import me.birb.resonantengineering.network.energynode.EnergyNode;
import me.birb.resonantengineering.network.energynode.EnergyProducer;
import me.birb.resonantengineering.network.energynode.PeerState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyNodeState<NodeType extends EnergyNode> implements PeerState {

    private NodeType node;
    private World world;
    private BlockPos pos;

    public EnergyNodeState(NodeType node, World world, BlockPos pos) {
        this.node = node;
        this.world = world;
        this.pos = pos;
    }

    public NodeType getNode() {
        return node;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void setNetwork(EnergyNetwork network) {
        node.setNetwork(pos, world, network);
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnergyNetwork getNetwork() {
        return node.getNetwork(world, pos);
    }

    public static EnergyNodeState<?> getCastedInstance(World world, BlockPos pos, EnergyNode node) {
        if(node instanceof EnergyConsumer) {
            return new EnergyConsumerState((EnergyConsumer)node, world, pos);
        } else if(node instanceof EnergyProducer) {
            return new EnergyProducerState((EnergyProducer)node, world, pos);
        }
        return new EnergyNodeState<>(node, world, pos);
    }

}
