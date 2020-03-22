package me.birb.thermalexpansion.network.energynode.state;

import me.birb.thermalexpansion.network.energynode.EnergyNode;
import me.birb.thermalexpansion.network.energynode.EnergyProducer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyProducerState extends EnergyNodeState<EnergyProducer> {

    public EnergyProducerState(EnergyProducer node, World world, BlockPos pos) {
        super(node, world, pos);
    }

}
