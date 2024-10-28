package com.github.elenterius.biofactory.integration.biomancy;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biomancy.api.nutrients.Nutrients;
import com.github.elenterius.biomancy.api.nutrients.fluid.FluidNutrients;
import com.github.elenterius.biomancy.api.nutrients.fluid.FluidToFuelConversion;
import com.github.elenterius.biomancy.api.tribute.MilliTribute;
import com.github.elenterius.biomancy.api.tribute.MobEffectTribute;
import com.github.elenterius.biomancy.api.tribute.MobEffectTribute.Builder;
import com.github.elenterius.biomancy.api.tribute.Tribute;
import com.github.elenterius.biomancy.api.tribute.Tributes;
import com.github.elenterius.biomancy.api.tribute.fluid.FluidToTributeConversion;
import com.github.elenterius.biomancy.api.tribute.fluid.FluidTributes;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.fluids.FluidStack;

public final class BiomancyIntegration {

	public static final int GLASS_BOTTLE_AMOUNT = 250;
	public static final int BUCKET_AMOUNT = 1000;

	public static final FluidToFuelConversion FLUID_TO_FUEL_CONVERSION = new FluidToFuelConversion() {
		public int getFuelMultiplier(FluidStack resource) {
			return 1;
		}

		public int getFluidToFuelRatio(FluidStack resource) {
			return 10;
		}
	};

	public static final FluidToTributeConversion POTION_FLUID_TO_TRIBUTE_CONVERSION = resource -> {
		CompoundTag tag = resource.getOrCreateTag();

		Builder builder = MobEffectTribute.builder();
		PotionUtils.getAllEffects(tag).forEach(builder::addEffect);
		MobEffectTribute effectTribute = builder.build();

		return MilliTribute.convertToMilliUnit(effectTribute, GLASS_BOTTLE_AMOUNT);
	};

	private static final Tribute BUILDER_TEA_TRIBUTE = Tributes.combineTributes(
		MobEffectTribute.builder().addEffect(MobEffects.DIG_SPEED, 0, 3 * 60 * 20, 1f).build(),
		Tributes.getTribute(Items.MILK_BUCKET.getDefaultInstance())
	);
	private static final MilliTribute BUILDER_TEA_MILLI_TRIBUTE = MilliTribute.convertToMilliUnit(BUILDER_TEA_TRIBUTE, GLASS_BOTTLE_AMOUNT);
	private static final MilliTribute HONEY_MILLI_TRIBUTE = MilliTribute.convertToMilliUnit(Tributes.getTribute(Items.HONEY_BOTTLE.getDefaultInstance()), GLASS_BOTTLE_AMOUNT);

	private BiomancyIntegration() {
	}

	public static void onPostSetup() {
		FluidNutrients.register(ModFluids.NUTRIENTS_FLUID, FLUID_TO_FUEL_CONVERSION);

		Tributes.register(AllItems.BUILDERS_TEA.get(), BUILDER_TEA_TRIBUTE);

		FluidTributes.register(AllFluids.TEA.get(), resource -> BUILDER_TEA_MILLI_TRIBUTE);
		FluidTributes.register(AllFluids.POTION.get(), POTION_FLUID_TO_TRIBUTE_CONVERSION);
		FluidTributes.register(AllFluids.HONEY.get(), resource -> HONEY_MILLI_TRIBUTE);
	}

	public static int convertTofluidAmount(ItemStack resource) {
		int fuelMultiplier = FLUID_TO_FUEL_CONVERSION.getFuelMultiplier(null);
		int fluidToFuelRatio = FLUID_TO_FUEL_CONVERSION.getFluidToFuelRatio(null);
		return Nutrients.getFuelValue(resource) * fluidToFuelRatio / fuelMultiplier;
	}

	public static double convertToFuelAmount(int fluidAmount) {
		int fuelMultiplier = FLUID_TO_FUEL_CONVERSION.getFuelMultiplier(null);
		int fluidToFuelRatio = FLUID_TO_FUEL_CONVERSION.getFluidToFuelRatio(null);
		return (double) fluidAmount / fluidToFuelRatio * fuelMultiplier;
	}

}
