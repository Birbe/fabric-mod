package me.birb.resonantengineering.block.machine;

import me.birb.resonantengineering.network.ConduitState;
import me.birb.resonantengineering.network.energynode.EnergyConsumer;
import me.birb.resonantengineering.network.EnergyNetwork;
import me.birb.resonantengineering.network.NetworkManager;
import me.birb.resonantengineering.network.energynode.state.EnergyConsumerState;
import me.birb.resonantengineering.network.energynode.state.EnergyNodeState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Machine extends Block implements EnergyConsumer, BlockEntityProvider {

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
    public EnergyNetwork getNetwork(World world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof MachineBlockEntity) {
            return NetworkManager.getNetwork(((MachineBlockEntity)entity).networks.get(0));
        }
        return null;
    }

    public List<EnergyNetwork> getNetworks(BlockPos pos, World world) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof MachineBlockEntity) {
            List<Integer> ids = ((MachineBlockEntity)entity).networks;
            ArrayList<EnergyNetwork> networks = new ArrayList<>();
            for (int id : ids) {
                networks.add(NetworkManager.getNetwork(id));
            }
            return networks;
        }
        return new ArrayList<>();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(direction);
    }

    @Override
    public void setNetwork(BlockPos pos, World world, EnergyNetwork network) {
//        BlockEntity entity = world.getBlockEntity(pos);
//        if(entity instanceof MachineBlockEntity) {
//            ((MachineBlockEntity)entity).networkId = network.getId();
//        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        discoverNetworks(pos, world);
    }

    private void discoverNetworks(BlockPos pos, World world) {
        List<ConduitState> peers = getConduitPeers(pos, world);
        ArrayList<EnergyNetwork> networks = new ArrayList<>();
        for(ConduitState peer : peers) {
            if(!networks.contains(peer.getNetwork())) {
                networks.add(peer.getNetwork());
            }
        }
        for(EnergyNetwork network : networks) {
            boolean exists = false;
            for(EnergyNodeState<?> state : network.getNodes()) {
                if(state.getPos().getX() == pos.getX() && state.getPos().getY() == pos.getY() && state.getPos().getZ() == pos.getZ()) {
                    //Network contains state
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                network.addNode(new EnergyConsumerState(this, world, pos));
            }
        }
        setNetworks(pos, world, networks);
    }

    public void setNetworks(BlockPos pos, World world, ArrayList<EnergyNetwork> networks) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof MachineBlockEntity) {
            ArrayList<Integer> ids = new ArrayList<>();
            networks.forEach(network -> ids.add(network.getId()));
            ((MachineBlockEntity)entity).networks = ids;
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient()) getNetworks(pos, world).forEach(network -> MinecraftClient.getInstance().player.addChatMessage(
                new LiteralText(String.valueOf(network.getId())), false
        ));

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void energyTick() {

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return null;
    }
}
