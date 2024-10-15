package com.github.elenterius.biofactory.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import java.util.List;
import java.util.Set;

public class ModLootTableProvider extends LootTableProvider {

	public static final Marker LOG_MARKER = MarkerManager.getMarker("LootTableProvider");

	public ModLootTableProvider(PackOutput packOutput) {
		super(packOutput, Set.of(), createSubProviders(packOutput));
	}

	private static List<SubProviderEntry> createSubProviders(PackOutput output) {
		return List.of(
			new SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK)
		);
	}

}