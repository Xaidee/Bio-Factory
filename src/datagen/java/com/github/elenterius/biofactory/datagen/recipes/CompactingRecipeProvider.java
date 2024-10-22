package com.github.elenterius.biofactory.datagen.recipes;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biofactory.integration.biomancy.BiomancyIntegration;
import com.github.elenterius.biomancy.init.ModItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.PackOutput;

public class CompactingRecipeProvider extends ProcessingRecipeProvider {

	protected CompactingRecipeProvider(PackOutput generator) {
		super(generator, AllRecipeTypes.COMPACTING);
	}

	@Override
	protected void init() {
		create("nutrient_bar_from_fluid", recipeBuilder -> {
				int fluidAmount = BiomancyIntegration.convertTofluidAmount(ModItems.NUTRIENT_BAR.get().getDefaultInstance());
				if (fluidAmount <= 0) {throw new IllegalArgumentException("Cannot create nutrients_fluid_gel_from_paste with amount of 0");}
				return recipeBuilder
					.require(ModFluids.NUTRIENTS_FLUID.get(), fluidAmount)
					.requiresHeat(HeatCondition.HEATED)
					.output(ModItems.NUTRIENT_BAR.get());
			}
		);

		create("flesh_block_from_flesh_bits", recipeBuilder -> recipeBuilder
			.require(ModItems.FLESH_BITS.get()).require(ModItems.FLESH_BITS.get())
			.require(ModItems.FLESH_BITS.get()).require(ModItems.FLESH_BITS.get())
			.output(ModItems.FLESH_BLOCK.get())
		);

		create("packed_flesh_block_from_flesh_blocks", recipeBuilder -> recipeBuilder
			.require(ModItems.FLESH_BLOCK.get()).require(ModItems.FLESH_BLOCK.get())
			.require(ModItems.TOUGH_FIBERS.get()).require(ModItems.TOUGH_FIBERS.get())
			.require(ModItems.TOUGH_FIBERS.get()).require(ModItems.TOUGH_FIBERS.get())
			.output(ModItems.PACKED_FLESH_BLOCK.get())
		);

		create("malignant_flesh_block_from_veins", recipeBuilder -> recipeBuilder
			.require(ModItems.MALIGNANT_FLESH_VEINS.get()).require(ModItems.MALIGNANT_FLESH_VEINS.get())
			.require(ModItems.MALIGNANT_FLESH_VEINS.get()).require(ModItems.MALIGNANT_FLESH_VEINS.get())
			.require(ModItems.FLESH_BITS.get())
			.require(ModItems.MALIGNANT_FLESH_VEINS.get()).require(ModItems.MALIGNANT_FLESH_VEINS.get())
			.require(ModItems.MALIGNANT_FLESH_VEINS.get()).require(ModItems.MALIGNANT_FLESH_VEINS.get())
			.output(ModItems.MALIGNANT_FLESH_BLOCK.get())
		);

		create("primal_flesh_block_from_malignant_flesh_block", recipeBuilder -> recipeBuilder
			.require(ModItems.MALIGNANT_FLESH_BLOCK.get()).require(ModItems.MALIGNANT_FLESH_BLOCK.get())
			.require(ModItems.MALIGNANT_FLESH_BLOCK.get()).require(ModItems.MALIGNANT_FLESH_BLOCK.get())
			.require(ModItems.FLESH_BITS.get())
			.require(ModItems.MALIGNANT_FLESH_BLOCK.get()).require(ModItems.MALIGNANT_FLESH_BLOCK.get())
			.require(ModItems.MALIGNANT_FLESH_BLOCK.get()).require(ModItems.MALIGNANT_FLESH_BLOCK.get())
			.output(ModItems.PRIMAL_FLESH_BLOCK.get())
		);
	}

}
