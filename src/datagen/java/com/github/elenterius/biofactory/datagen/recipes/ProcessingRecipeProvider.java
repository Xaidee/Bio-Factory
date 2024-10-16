package com.github.elenterius.biofactory.datagen.recipes;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public abstract class ProcessingRecipeProvider extends ProcessingRecipeGen {

	private final IRecipeTypeInfo recipeTypeInfo;

	protected ProcessingRecipeProvider(PackOutput output, IRecipeTypeInfo recipeTypeInfo) {
		super(output);
		this.recipeTypeInfo = recipeTypeInfo;
		init();
	}

	protected abstract void init();

	@Override
	protected IRecipeTypeInfo getRecipeType() {
		return recipeTypeInfo;
	}

	protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(Supplier<ItemLike> singleIngredient, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return create(BioFactoryMod.MOD_ID, singleIngredient, transform);
	}

	protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(String name, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return create(BioFactoryMod.createRL(name), transform);
	}

	@Override
	protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
		return () -> {
			ResourceLocation registryName = RegisteredObjects.getKeyOrThrow(item.get().asItem());
			return BioFactoryMod.createRL(registryName.getPath() + suffix);
		};
	}

}
