package me.birb.thermalexpansion.network.energynode.state;

import me.birb.thermalexpansion.network.energynode.EnergyConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyConsumerState extends EnergyNodeState<EnergyConsumer> {
    public EnergyConsumerState(EnergyConsumer node, World world, BlockPos pos) {
        super(node, world, pos);
    }
}
