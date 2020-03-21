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

    public static DirectionProperty direction = DirectionProperty.of("direction", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

    public RedstoneFurnace(Settings settings) {
        super(settings);

        setDefaultState(getStateManager().getDefaultState().with(direction, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(direction);
    }

//    @Override
//    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
//        //Direction facing = Direction.fromRotation(placer.getHeadYaw()).getOpposite();
//        //world.setBlockState(pos, world.getBlockState(pos).with(direction, facing));
//    }


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
    public void setNetwork(EnergyNetwork network) {

    }

    @Override
    public void energyTick() {

    }
}
