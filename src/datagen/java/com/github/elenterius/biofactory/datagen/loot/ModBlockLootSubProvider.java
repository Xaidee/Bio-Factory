package com.github.elenterius.biofactory.datagen.loot;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.init.ModBlocks;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class ModBlockLootSubProvider extends BlockLootSubProvider {

	protected ModBlockLootSubProvider() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {
		add(ModBlocks.NUTRIENTS_FLUID_GEL.get(), noDrop());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		List<Block> blocks = ModBlocks.BLOCKS.getEntries().stream().map(Supplier::get).toList();
		BioFactoryMod.LOGGER.info(ModLootTableProvider.LOG_MARKER, "generating loot tables for {} blocks...", blocks.size());
		return blocks;
	}

}
