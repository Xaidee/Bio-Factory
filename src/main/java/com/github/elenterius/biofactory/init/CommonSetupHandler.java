package com.github.elenterius.biofactory.init;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.integration.biomancy.BiomancyIntegration;
import com.github.elenterius.biofactory.integration.create.CreateIntegration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BioFactoryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CommonSetupHandler {

	private CommonSetupHandler() {}

	@SubscribeEvent
	public static void onSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			BiomancyIntegration.onPostSetup();
			CreateIntegration.onPostSetup();
		});

		ModFluids.registerInteractions();
	}

}
