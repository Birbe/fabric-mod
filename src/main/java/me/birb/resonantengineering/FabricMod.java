package me.birb.resonantengineering;

import me.birb.resonantengineering.block.AmberBlock;
import me.birb.resonantengineering.block.conduit.RedstoneConduit;
import me.birb.resonantengineering.block.conduit.RedstoneConduitEntity;
import me.birb.resonantengineering.block.machine.Evaporator;
import me.birb.resonantengineering.block.machine.MachineBlockEntity;
import me.birb.resonantengineering.block.machine.RedstoneFurnace;
import me.birb.resonantengineering.item.AmberItem;
import net.fabricmc.api.ModInitializer;
import me.birb.resonantengineering.block.AmberOre;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
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
	public static BlockEntityType<MachineBlockEntity> MACHINE_BLOCK_ENTITY;

	public static final Item EMPTY_CONDUIT_FRAME = new Item(new Item.Settings().group(ItemGroup.REDSTONE));

	public static final Block EVAPORATOR = new Evaporator(FabricBlockSettings.of(Material.METAL).build());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registry.BLOCK, new Identifier("resonantengineering","amber_ore"), AMBER_ORE);
		Registry.register(Registry.ITEM, new Identifier("resonantengineering", "amber_ore"), new BlockItem(AMBER_ORE, new Item.Settings().group(ItemGroup.DECORATIONS)));

		Registry.register(Registry.BLOCK, new Identifier("resonantengineering","amber_block"), AMBER_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("resonantengineering", "amber_block"), new BlockItem(AMBER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

		Registry.register(Registry.ITEM, new Identifier("resonantengineering", "amber_item"), AMBER_ITEM);

		Registry.register(Registry.BLOCK, new Identifier("resonantengineering", "redstone_conduit"), REDSTONE_CONDUIT);
		Registry.register(Registry.ITEM, new Identifier("resonantengineering", "redstone_conduit"), new BlockItem(REDSTONE_CONDUIT, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier("resonantengineering", "redstone_furnace"), REDSTONE_FURNACE);
		Registry.register(Registry.ITEM, new Identifier("resonantengineering", "redstone_furnace"), new BlockItem(REDSTONE_FURNACE, new Item.Settings().group(ItemGroup.MATERIALS)));

		Registry.register(Registry.BLOCK, new Identifier("resonantengineering", "evaporator"), EVAPORATOR);
		Registry.register(Registry.ITEM, new Identifier("resonantengineering", "evaporator"), new BlockItem(EVAPORATOR, new Item.Settings().group(ItemGroup.BREWING)));

		REDSTONE_CONDUIT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "resonantengineering:redstone_conduit", BlockEntityType.Builder.create(RedstoneConduitEntity::new, REDSTONE_CONDUIT).build(null));
		MACHINE_BLOCK_ENTITY    = Registry.register(Registry.BLOCK_ENTITY_TYPE, "resonantengineering:redstone_furnace", BlockEntityType.Builder.create(MachineBlockEntity::new, REDSTONE_FURNACE).build(null));

		Registry.register(Registry.ITEM, new Identifier("resonantengineering", "empty_conduit_frame"), EMPTY_CONDUIT_FRAME);

		BlockRenderLayerMap.INSTANCE.putBlock(REDSTONE_CONDUIT, RenderLayer.getTranslucent());
	}
}
