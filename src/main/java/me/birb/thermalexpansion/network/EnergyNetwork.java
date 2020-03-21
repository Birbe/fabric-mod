package me.birb.thermalexpansion.network;

import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongLists;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EnergyNetwork {

    private int maxCapacity;
    private int currentFlow;

    private ArrayList<EnergyConsumer> consumers = new ArrayList<>(); //Nodes are either producers or consumers.
    private ArrayList<EnergyProducer> producers = new ArrayList<>();

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

    public void setCurrentFlow(int currentFlow) {
        this.currentFlow = currentFlow;
    }

    public void setConsumers(ArrayList<EnergyConsumer> consumers) {
        this.consumers = consumers;
    }

    public void setProducers(ArrayList<EnergyProducer> producers) {
        this.producers = producers;
    }

    public World getWorld() {
        return world;
    }

    public void addProducer(EnergyProducer producer) {
        producer.setNetwork(this);
        producers.add(producer);
    }

    public void removeProducer(EnergyNode node) {
        producers.remove(node);
    }

    public ArrayList<EnergyProducer> getProducers() {
        return producers;
    }

    public void addConsumer(EnergyConsumer consumer) {
        consumer.setNetwork(this);
        consumers.add(consumer);
    }

    public void removeConsumer(EnergyConsumer consumer) {
        consumers.remove(consumer);
    }

    public ArrayList<EnergyConsumer> getConsumers() {
        return consumers;
    }

}
