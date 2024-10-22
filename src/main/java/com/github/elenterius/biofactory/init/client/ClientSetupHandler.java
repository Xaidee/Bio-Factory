package com.github.elenterius.biofactory.init.client;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.init.ModFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BioFactoryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetupHandler {

	private ClientSetupHandler() {}

	@SubscribeEvent
	public static void onSetup(final FMLClientSetupEvent event) {
		setBlockRenderLayers();

		event.enqueueWork(ClientSetupHandler::onPostSetup);
	}

	private static void onPostSetup() {
		registerItemModelProperties();
	}

	@SubscribeEvent
	public static void registerKeyMappings(final RegisterKeyMappingsEvent event) {}

	@SubscribeEvent
	public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {}

	private static void setBlockRenderLayers() {
		ItemBlockRenderTypes.setRenderLayer(ModFluids.NUTRIENTS_FLUID.get(), RenderType.translucent());
	}

	@SubscribeEvent
	public static void registerParticles(final RegisterParticleProvidersEvent event) {}

	@SubscribeEvent
	public static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {}

	private static void registerItemModelProperties() {}

	@SubscribeEvent
	public static void onItemColorRegistry(final RegisterColorHandlersEvent.Item event) {}

	@SubscribeEvent
	public static void onBlockColorRegistry(final RegisterColorHandlersEvent.Block event) {}

}
