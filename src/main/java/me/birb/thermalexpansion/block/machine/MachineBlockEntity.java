package me.birb.thermalexpansion.block.machine;

import me.birb.thermalexpansion.FabricMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class MachineBlockEntity extends BlockEntity {

    public List<Integer> networks = new ArrayList<>();

    public MachineBlockEntity() {
        super(FabricMod.MACHINE_BLOCK_ENTITY);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        networks.clear();
        int[] ids = tag.getIntArray("networks");
        for(int id : ids) {
            networks.add(id);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putIntArray("networks", networks);
        return tag;
    }
}
