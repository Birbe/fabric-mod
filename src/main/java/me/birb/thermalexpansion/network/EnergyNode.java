package me.birb.thermalexpansion.network;

public interface EnergyNode {

    EnergyNetwork getNetwork();

    void setNetwork(EnergyNetwork network);

    void energyTick();

}
