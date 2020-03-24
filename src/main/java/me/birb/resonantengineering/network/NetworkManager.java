package me.birb.resonantengineering.network;

import me.birb.resonantengineering.network.energynode.state.EnergyNodeState;

import java.util.ArrayList;

public class NetworkManager {

    public static ArrayList<EnergyNetwork> networks = new ArrayList<>();

    public static ArrayList<EnergyNetwork> getNetworks() {
        return networks;
    }

    public static EnergyNetwork mergeNetworks(EnergyNetwork ...mergingNetworks) {
        return mergeNetworks(mergingNetworks);
    }

    public static void removeNetwork(EnergyNetwork network) {
        if(network != null) networks.remove(network);
    }

    public static EnergyNetwork mergeNetworks(Iterable<EnergyNetwork> mergingNetworks) {
        EnergyNetwork merged = new EnergyNetwork();

        for(EnergyNetwork network : mergingNetworks) {
            ArrayList<EnergyNodeState<?>> nodes = network.getNodes();

            for(EnergyNodeState<?> node : nodes) {
                merged.addNode(node);
            }

            networks.remove(network);
        }

        networks.add(merged);

        return merged;
    }

    public static EnergyNetwork getNetwork(int id) {
        return networks.get(id);
    }

    public static void registerNetwork(EnergyNetwork network) {
        //networks.trimToSize();
        for(int i=0;i<networks.size();i++) { //Put a network in a free ID if possible
            if(networks.get(i) == null) {
                networks.set(i, network);
                return;
            }
        }
        networks.add(network);
    }

    public static int getNetworkId(EnergyNetwork network) {
        return networks.indexOf(network);
    }

}
