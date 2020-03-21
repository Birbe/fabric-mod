package me.birb.thermalexpansion.block.conduit;

import me.birb.thermalexpansion.ClientMod;
import me.birb.thermalexpansion.network.ConduitState;
import me.birb.thermalexpansion.network.EnergyConduit;
import me.birb.thermalexpansion.network.EnergyConsumer;
import me.birb.thermalexpansion.network.EnergyNetwork;
import me.birb.thermalexpansion.network.EnergyProducer;
import me.birb.thermalexpansion.network.NetworkManager;
import me.birb.thermalexpansion.util.StringText;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import sun.nio.ch.Net;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.birb.thermalexpansion.FabricMod.REDSTONE_CONDUIT;

public class RedstoneConduit extends Block implements EnergyConduit, BlockEntityProvider {

    public static final BooleanProperty north = BooleanProperty.of("north");
    public static final BooleanProperty east = BooleanProperty.of("east");
    public static final BooleanProperty south = BooleanProperty.of("south");
    public static final BooleanProperty west = BooleanProperty.of("west");
    public static final BooleanProperty up = BooleanProperty.of("up");
    public static final BooleanProperty down = BooleanProperty.of("down");

    public static final BooleanProperty[] DIRECTIONS = {
            up, down, north, south, west, east
    };

    public RedstoneConduit(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(north, false).with(east, false).with(south, false).with(west, false).with(up, false).with(down, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(north, east, south, west, up, down);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return VoxelShapes.cuboid(5D/16D, 5D/16D, 5D/16D, 11D/16D, 11D/16D, 11D/16D);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        updateBlockState(world, pos, pos); //Figure out what peers are nearby so that multipart can render correctly
        for(Direction direction : Direction.values()) { //Tell neighbours to do so
            if(world.getBlockState(pos.offset(direction)).getBlock() instanceof RedstoneConduit) updateBlockState(world, pos.offset(direction));
        }
        discoverNetwork(world, pos);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        //updateBlockState(world, pos, pos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()) {
            EnergyNetwork network = getNetwork(world, pos);
            if (network != null) {
                int id = NetworkManager.getNetworkId(network);
                MinecraftClient.getInstance().player.addChatMessage(new LiteralText(String.valueOf(id)), false);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        for(Direction direction : Direction.values()) {
            if(world.getBlockState(pos.offset(direction)).getBlock() instanceof RedstoneConduit) updateBlockState(world, pos.offset(direction), pos);
        }

        //Update the network
        List<ConduitState> peers = getPeers(world, pos);
        ArrayList<ConduitState> seenPeers = new ArrayList<>();
        seenPeers.add(new ConduitState(pos, world, this));
        for(ConduitState conduit : peers) {
            if(!arrayContainsSimilarState(seenPeers, conduit)) {
                conduit.recurseDiscoverNetwork(world, conduit.getPos(), peers, seenPeers);
            }
        }
    }

    private boolean arrayContainsSimilarState(List<ConduitState> list, ConduitState target) {
        for(ConduitState s : list) {
            if(s.getPos().getX() == target.getPos().getX() && s.getPos().getY() == target.getPos().getY() && s.getPos().getZ() == target.getPos().getZ()) {
                return true;
            }
        }
        return false;
    }

    public void updateBlockState(World world, BlockPos pos) {
        updateBlockState(world, pos, null);
    }

    public void updateBlockState(World world, BlockPos pos, BlockPos disregard) {
        BlockState state = world.getBlockState(pos);
        for(Direction direction : Direction.values()) {
            BooleanProperty prop = DIRECTIONS[direction.ordinal()];
            Block peerBlock = world.getBlockState(pos.offset(direction)).getBlock();
            boolean isConduit = peerBlock instanceof EnergyConduit || peerBlock instanceof EnergyConsumer || peerBlock instanceof EnergyProducer;
            if(pos.offset(direction).equals(disregard)) isConduit = false;

            BlockState newState = state.with(prop, isConduit);
            world.setBlockState(pos, newState);
            state = newState;
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new RedstoneConduitEntity();
    }

    @Override
    @Nullable
    public EnergyNetwork getNetwork(World world, BlockPos pos) {
        return NetworkManager.getNetwork(((RedstoneConduitEntity)world.getBlockEntity(pos)).networkId);
    }

    public void setNetwork(World world, BlockPos pos, EnergyNetwork network) {
        setNetwork(world, pos, NetworkManager.getNetworkId(network));
    }

    public void setNetwork(World world, BlockPos pos, int id) {
        ((RedstoneConduitEntity) world.getBlockEntity(pos)).networkId = id;
    }

    public List<ConduitState> getPeers(World world, BlockPos pos) {
        ArrayList<ConduitState> peers = new ArrayList<>();

        for(Direction direction : Direction.values()) {

            BlockPos peerPos = pos.offset(direction);
            BlockState peerState = world.getBlockState(peerPos);

            if (peerState != null) {
                if(peerState.getBlock() instanceof EnergyConduit) {
                    Block peerBlock = world.getBlockState(peerPos).getBlock();
                    peers.add(new ConduitState(peerPos, world, (EnergyConduit) peerBlock));
                }
            }
        }

        return peers;
    }

    public void discoverNetwork(World world, BlockPos pos) {
        List<ConduitState> peers = getPeers(world, pos);

        ConduitState.DiscoveredNetwork network = new ConduitState(pos, world, (EnergyConduit) REDSTONE_CONDUIT).recurseDiscoverNetwork(peers);
        NetworkManager.addNetwork(network.network);
        setNetwork(world, pos, network.network);
    }
}
