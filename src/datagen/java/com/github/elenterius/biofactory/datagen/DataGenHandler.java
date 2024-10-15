package com.github.elenterius.biofactory.datagen;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.datagen.loot.ModLootTableProvider;
import com.github.elenterius.biofactory.datagen.models.ModBlockStateProvider;
import com.github.elenterius.biofactory.datagen.models.ModItemModelProvider;
import com.github.elenterius.biofactory.datagen.tags.ModBlockTagsProvider;
import com.github.elenterius.biofactory.datagen.tags.ModItemTagsProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BioFactoryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenHandler {

	private DataGenHandler() {}

	@SubscribeEvent
	public static void generateData(final GatherDataEvent event) {
		boolean includeServer = event.includeServer();
		boolean includeClient = event.includeClient();

		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

		//tags
		ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
		generator.addProvider(includeServer, blockTagsProvider);
		generator.addProvider(includeServer, new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

		//recipes
//		generator.addProvider(includeServer, new ModRecipeProvider(packOutput));

		//loot
		generator.addProvider(includeServer, new ModLootTableProvider(packOutput));

		//models & block states
		generator.addProvider(includeServer, new ModBlockStateProvider(packOutput, existingFileHelper));
		generator.addProvider(includeServer, new ModItemModelProvider(packOutput, existingFileHelper));

		//translations
		LanguageProvider enLanguage = new ModLanguageProvider(packOutput);

		//advancements
//		generator.addProvider(includeServer, new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper, enLanguage));

		generator.addProvider(includeServer, enLanguage);
	}

}
