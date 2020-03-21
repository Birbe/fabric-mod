package me.birb.thermalexpansion;

import me.birb.thermalexpansion.block.AmberBlock;
import me.birb.thermalexpansion.block.conduit.RedstoneConduit;
import me.birb.thermalexpansion.block.conduit.RedstoneConduitEntity;
import me.birb.thermalexpansion.block.machine.RedstoneFurnace;
import me.birb.thermalexpansion.item.AmberItem;
import net.fabricmc.api.ModInitializer;
import me.birb.thermalexpansion.block.AmberOre;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricMod implements ModInitializer {

	public static final Block AMBER_ORE =  new AmberOre(FabricBlockSettings.of(Material.STONE).build());
	public static final Block AMBER_BLOCK = new AmberBlock(FabricBlockSettings.of(Material.STONE).build());
	public static final Item AMBER_ITEM = new AmberItem(new Item.Settings().group(ItemGroup.DECORATIONS));

	public static final Block REDSTONE_CONDUIT = new RedstoneConduit(FabricBlockSettings.of(Material.ORGANIC).nonOpaque().build());
	public static BlockEntityType<RedstoneConduitEntity> REDSTONE_CONDUIT_ENTITY;

	public static final Block REDSTONE_FURNACE = new RedstoneFurnace(FabricBlockSettings.of(Material.METAL).build());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registry.BLOCK, new Identifier("thermalexpansion","amber_ore"), AMBER_ORE);
		Registry.register(Registry.ITEM, new Identifier("thermalexpansion", "amber_ore"), new BlockItem(AMBER_ORE, new Item.Settings().group(ItemGroup.DECORATIONS)));

		Registry.register(Registry.BLOCK, new Identifier("thermalexpansion","amber_block"), AMBER_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("thermalexpansion", "amber_block"), new BlockItem(AMBER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

		Registry.register(Registry.ITEM, new Identifier("thermalexpansion", "amber_item"), AMBER_ITEM);

		Registry.register(Registry.BLOCK, new Identifier("thermalexpansion", "redstone_conduit"), REDSTONE_CONDUIT);
		Registry.register(Registry.ITEM, new Identifier("thermalexpansion", "redstone_conduit"), new BlockItem(REDSTONE_CONDUIT, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier("thermalexpansion", "redstone_furnace"), REDSTONE_FURNACE);
		Registry.register(Registry.ITEM, new Identifier("thermalexpansion", "redstone_furnace"), new BlockItem(REDSTONE_FURNACE, new Item.Settings().group(ItemGroup.MATERIALS)));

		REDSTONE_CONDUIT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "thermalexpansion:redstone_conduit", BlockEntityType.Builder.create(RedstoneConduitEntity::new, REDSTONE_CONDUIT).build(null));
	}
}
