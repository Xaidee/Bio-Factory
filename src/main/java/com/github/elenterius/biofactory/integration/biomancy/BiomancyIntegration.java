package com.github.elenterius.biofactory.integration.biomancy;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biomancy.api.nutrients.Nutrients;
import com.github.elenterius.biomancy.api.nutrients.fluid.FluidNutrients;
import com.github.elenterius.biomancy.api.nutrients.fluid.FluidToFuelConversion;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public final class BiomancyIntegration {

	public static final FluidToFuelConversion FLUID_TO_FUEL_CONVERSION = new FluidToFuelConversion() {
		public int getFuelMultiplier(FluidStack resource) {
			return 1;
		}

		public int getFluidToFuelRatio(FluidStack resource) {
			return 10;
		}
	};

	private BiomancyIntegration() {}

	public static void onPostSetup() {
		FluidNutrients.registerFuel(ModFluids.NUTRIENTS_FLUID_GEL_TYPE, FLUID_TO_FUEL_CONVERSION);
	}

	public static int convertTofluidAmount(ItemStack resource) {
		int fuelMultiplier = FLUID_TO_FUEL_CONVERSION.getFuelMultiplier(null);
		int fluidToFuelRatio = FLUID_TO_FUEL_CONVERSION.getFluidToFuelRatio(null);
		return Nutrients.getFuelValue(resource) * fluidToFuelRatio / fuelMultiplier;
	}

}
