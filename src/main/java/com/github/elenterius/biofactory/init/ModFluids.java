package com.github.elenterius.biofactory.init;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.simibubi.create.content.fluids.VirtualFluid;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModFluids {

	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, BioFactoryMod.MOD_ID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, BioFactoryMod.MOD_ID);

	public static final RegistryObject<FluidType> NUTRIENTS_TYPE = registerType("nutrients_fluid", properties -> properties);
	public static final Supplier<ForgeFlowingFluid.Properties> NUTRIENTS_FLUID_PROPERTIES = () -> new ForgeFlowingFluid.Properties(NUTRIENTS_TYPE, ModFluids.NUTRIENTS_FLUID,
		ModFluids.NUTRIENTS_FLUID);
	public static final RegistryObject<ForgeFlowingFluid> NUTRIENTS_FLUID = register("nutrients_fluid", () -> new VirtualFluid(NUTRIENTS_FLUID_PROPERTIES.get(), true));

	private ModFluids() {}

	static void registerInteractions() {}

	private static <T extends Fluid> RegistryObject<T> register(String name, Supplier<T> factory) {
		return FLUIDS.register(name, factory);
	}

	private static RegistryObject<FluidType> registerType(String name, UnaryOperator<FluidType.Properties> operator) {
		return FLUID_TYPES.register(name, () -> new FluidType(operator.apply(createFluidTypeProperties())) {

			private final ResourceLocation stillTexture = BioFactoryMod.createRL("block/%s_still".formatted(name));
			private final ResourceLocation flowingTexture = BioFactoryMod.createRL("block/%s_flowing".formatted(name));
			private final ResourceLocation overlayTexture = BioFactoryMod.createRL("block/%s_overlay".formatted(name));
			private final ResourceLocation renderOverlayTexture = overlayTexture.withPrefix("textures/");

			@Override
			public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
				consumer.accept(new IClientFluidTypeExtensions() {
					@Override
					public ResourceLocation getStillTexture() {
						return stillTexture;
					}

					@Override
					public ResourceLocation getFlowingTexture() {
						return flowingTexture;
					}

					@Override
					public ResourceLocation getOverlayTexture() {
						return overlayTexture;
					}

					@Override
					public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
						return renderOverlayTexture;
					}
				});
			}
		});
	}

	private static FluidType.Properties createFluidTypeProperties() {
		return FluidType.Properties.create()
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
	}

}
