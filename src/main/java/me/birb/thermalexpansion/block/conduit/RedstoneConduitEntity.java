package me.birb.thermalexpansion.block.conduit;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

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
