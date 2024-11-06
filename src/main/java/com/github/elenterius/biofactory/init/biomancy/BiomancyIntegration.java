package com.github.elenterius.biofactory.init.biomancy;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biofactory.init.ModItems;
import com.github.elenterius.biofactory.item.NutrientsBottleItem;
import com.github.elenterius.biomancy.api.nutrients.Nutrients;
import com.github.elenterius.biomancy.api.nutrients.fluid.FluidNutrients;
import com.github.elenterius.biomancy.api.nutrients.fluid.FluidToFuelConversion;
import com.github.elenterius.biomancy.api.tribute.MobEffectTribute;
import com.github.elenterius.biomancy.api.tribute.MobEffectTribute.Builder;
import com.github.elenterius.biomancy.api.tribute.Tribute;
import com.github.elenterius.biomancy.api.tribute.Tributes;
import com.github.elenterius.biomancy.api.tribute.fluid.FluidToTributeConversion;
import com.github.elenterius.biomancy.api.tribute.fluid.FluidTribute;
import com.github.elenterius.biomancy.api.tribute.fluid.FluidTributes;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;

public final class BiomancyIntegration {

	public static final int GLASS_BOTTLE_AMOUNT = 250;
	//	public static final int BUCKET_AMOUNT = 1000;

	public static final FluidToFuelConversion FLUID_TO_FUEL_CONVERSION = fluidStack -> FluidToFuelConversion.MILLI_FUEL_SCALE / 10;

	public static final FluidToTributeConversion POTION_FLUID_TO_TRIBUTE_CONVERSION = resource -> {
		CompoundTag tag = resource.getOrCreateTag();

		Builder builder = MobEffectTribute.builder();
		PotionUtils.getAllEffects(tag).forEach(builder::addEffect);
		MobEffectTribute effectTribute = builder.build();

		return FluidTribute.of(effectTribute, GLASS_BOTTLE_AMOUNT);
	};

	private static final Tribute BUILDER_TEA_TRIBUTE = Tributes.combineTributes(
		MobEffectTribute.builder().addEffect(MobEffects.DIG_SPEED, 0, 3 * 60 * 20, 1f).build(),
		Tributes.getTribute(Items.MILK_BUCKET.getDefaultInstance())
	);
	private static final FluidTribute BUILDER_TEA_MILLI_TRIBUTE = FluidTribute.of(BUILDER_TEA_TRIBUTE, GLASS_BOTTLE_AMOUNT);
	private static final FluidTribute HONEY_MILLI_TRIBUTE = FluidTribute.of(Tributes.getTribute(Items.HONEY_BOTTLE.getDefaultInstance()), GLASS_BOTTLE_AMOUNT);

	private BiomancyIntegration() {
	}

	public static void onPostSetup() {
		int fuel = convertToFuelAmount(NutrientsBottleItem.FLUID_AMOUNT);
		Nutrients.registerFuel(ModItems.NUTRIENTS_BOTTLE.get(), fuel);
		Nutrients.registerRepairMaterial(ModItems.NUTRIENTS_BOTTLE.get(), fuel * 2);

		FluidNutrients.register(ModFluids.NUTRIENTS_FLUID, FLUID_TO_FUEL_CONVERSION);

		Tributes.register(AllItems.BUILDERS_TEA.get(), BUILDER_TEA_TRIBUTE);

		FluidTributes.register(AllFluids.TEA.get(), resource -> BUILDER_TEA_MILLI_TRIBUTE);
		FluidTributes.register(AllFluids.POTION.get(), POTION_FLUID_TO_TRIBUTE_CONVERSION);
		FluidTributes.register(AllFluids.HONEY.get(), resource -> HONEY_MILLI_TRIBUTE);

		ArmorUpgrades.register();
	}

	public static int convertToFluidAmount(ItemStack resource) {
		return Nutrients.getFuelValue(resource) * FluidToFuelConversion.MILLI_FUEL_SCALE / FLUID_TO_FUEL_CONVERSION.getMilliFuelPerUnit(null);
	}

	public static int convertToFuelAmount(int fluidAmount) {
		return fluidAmount * FLUID_TO_FUEL_CONVERSION.getMilliFuelPerUnit(null) / FluidToFuelConversion.MILLI_FUEL_SCALE;
	}

}
