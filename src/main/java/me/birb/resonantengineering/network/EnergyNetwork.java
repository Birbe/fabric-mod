package me.birb.resonantengineering.network;

import me.birb.resonantengineering.network.energynode.state.EnergyNodeState;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EnergyNetwork {

    private int maxCapacity;
    private int currentFlow;

    private ArrayList<EnergyNodeState<?>> nodes = new ArrayList<>(); //Nodes are either producers or consumers.
    private ArrayList<ConduitState> conduits = new ArrayList<>();

    private World world;

    public EnergyNetwork() {

    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentFlow() {
        return currentFlow;
    }

    public void setNodes(ArrayList<EnergyNodeState<?>> nodes) {
        this.nodes = nodes;
    }

    public World getWorld() {
        return world;
    }

    public void addNode(EnergyNodeState<?> node) {
        node.getNode().setNetwork(node.getPos(), node.getWorld(), this);
        nodes.add(node);
    }

    public ArrayList<EnergyNodeState<?>> getNodes() {
        return nodes;
    }

    public int getId() {
        return NetworkManager.getNetworkId(this);
    }

    public ArrayList<ConduitState> getConduits() {
        return conduits;
    }

    public void setConduits(ArrayList<ConduitState> conduits) {
        this.conduits = conduits;
    }

    public void removeConduit(ConduitState state) {

    }

}
