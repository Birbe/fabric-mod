package me.birb.thermalexpansion.network;

import java.util.ArrayList;

public class NetworkManager {

    public static ArrayList<EnergyNetwork> networks = new ArrayList<>();

    public static ArrayList<EnergyNetwork> getNetworks() {
        return networks;
    }

    public static EnergyNetwork mergeNetworks(EnergyNetwork ...mergingNetworks) {
        return mergeNetworks(mergingNetworks);
    }

    public static EnergyNetwork mergeNetworks(Iterable<EnergyNetwork> mergingNetworks) {
        EnergyNetwork merged = new EnergyNetwork();

        for(EnergyNetwork network : mergingNetworks) {
            ArrayList<EnergyProducer> producers = network.getProducers();
            ArrayList<EnergyConsumer> consumers = network.getConsumers();
            for(EnergyProducer node : producers) {
                merged.addProducer(node);
            }

            for(EnergyConsumer node : consumers) {
                merged.addConsumer(node);
            }

            networks.remove(network);
        }

        networks.add(merged);

        return merged;
    }

    public static EnergyNetwork getNetwork(int id) {
        return networks.get(id);
    }

    public static int addNetwork(EnergyNetwork network) {
        networks.add(network);
        return networks.indexOf(network);
    }

    public static int getNetworkId(EnergyNetwork network) {
        return networks.indexOf(network);
    }

}
