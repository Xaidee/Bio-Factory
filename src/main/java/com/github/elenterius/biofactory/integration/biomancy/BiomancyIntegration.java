package com.github.elenterius.biofactory.integration.biomancy;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biomancy.api.nutrients.FluidNutrients;
import com.github.elenterius.biomancy.api.nutrients.FluidToFuelConversion;

public final class BiomancyIntegration {

	private BiomancyIntegration() {}

	public static void onPostSetup() {
		FluidNutrients.registerFuel(ModFluids.NUTRIENTS_FLUID_GEL_TYPE, FluidToFuelConversion.IDENTITY);
	}

}
