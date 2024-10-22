package com.github.elenterius.biofactory.datagen.recipes;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biofactory.init.ModItems;
import com.github.elenterius.biofactory.item.NutrientsBottleItem;
import com.simibubi.create.AllRecipeTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class EmptyingRecipeProvider extends ProcessingRecipeProvider {

	protected EmptyingRecipeProvider(PackOutput generator) {
		super(generator, AllRecipeTypes.EMPTYING);
	}

	@Override
	protected void init() {
		create("nutrients_fluid_bottle", recipeBuilder -> recipeBuilder
			.require(ModItems.NUTRIENTS_BOTTLE.get())
			.output(ModFluids.NUTRIENTS_FLUID.get(), NutrientsBottleItem.FLUID_AMOUNT)
			.output(Items.GLASS_BOTTLE)
		);
	}

}
