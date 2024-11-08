package com.github.elenterius.biofactory.datagen.recipes;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biofactory.init.ModItems;
import com.github.elenterius.biofactory.item.NutrientsBottleItem;
import com.simibubi.create.AllRecipeTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class FillingRecipeProvider extends ProcessingRecipeProvider {

	protected FillingRecipeProvider(PackOutput generator) {
		super(generator, AllRecipeTypes.FILLING);
	}

	@Override
	protected void init() {
		create("nutrients_fluid_bottle", recipeBuilder -> recipeBuilder
			.require(ModFluids.NUTRIENTS_FLUID.get(), NutrientsBottleItem.FLUID_AMOUNT)
			.require(Items.GLASS_BOTTLE)
			.output(ModItems.NUTRIENTS_BOTTLE.get())
		);

		create("rich_soil", recipeBuilder -> recipeBuilder.whenModLoaded("farmersdelight")
			.require(ModFluids.NUTRIENTS_FLUID.get(), NutrientsBottleItem.FLUID_AMOUNT * 2)
			.require(Items.DIRT)
			.output(1, new ResourceLocation("farmersdelight", "rich_soil"), 1)
		);
	}

}
