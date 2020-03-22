package me.birb.thermalexpansion.block.machine;

import me.birb.thermalexpansion.network.EnergyConsumer;
import me.birb.thermalexpansion.network.EnergyNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public class Machine extends Block implements EnergyConsumer {

    private EnergyNetwork network;

    public static DirectionProperty direction = DirectionProperty.of("direction", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

    public Machine(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(direction, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(direction, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public EnergyNetwork getNetwork() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(direction);
    }

    @Override
    public void setNetwork(EnergyNetwork network) {

    }

    @Override
    public void energyTick() {

    }
}
