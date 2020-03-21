package me.birb.thermalexpansion.block.machine;

import me.birb.thermalexpansion.network.EnergyConsumer;
import me.birb.thermalexpansion.network.EnergyNetwork;
import net.minecraft.block.Block;

public class Machine extends Block implements EnergyConsumer {

    private EnergyNetwork network;

    public Machine(Settings settings) {
        super(settings);
    }


    @Override
    public EnergyNetwork getNetwork() {
        return null;
    }

    @Override
    public void setNetwork(EnergyNetwork network) {

    }

    @Override
    public void energyTick() {

    }
}
