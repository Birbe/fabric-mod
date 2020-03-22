package me.birb.thermalexpansion.block.machine;

import me.birb.thermalexpansion.network.EnergyConduit;
import me.birb.thermalexpansion.network.EnergyConsumer;
import me.birb.thermalexpansion.network.EnergyNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RedstoneFurnace extends Machine {

    public RedstoneFurnace(Settings settings) {
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
