package me.birb.thermalexpansion.block.conduit;

import me.birb.thermalexpansion.network.ConduitState;
import me.birb.thermalexpansion.network.EnergyConduit;
import me.birb.thermalexpansion.network.EnergyNetwork;
import me.birb.thermalexpansion.network.EnergyNode;
import me.birb.thermalexpansion.network.NetworkManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static me.birb.thermalexpansion.FabricMod.REDSTONE_CONDUIT;
import static me.birb.thermalexpansion.FabricMod.REDSTONE_CONDUIT_ENTITY;

public class RedstoneConduitEntity extends BlockEntity {

    public int networkId = -1;

    public RedstoneConduitEntity() {
        super(REDSTONE_CONDUIT_ENTITY);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        networkId = tag.getInt("network");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("network", networkId);

        return tag;
    }

}
