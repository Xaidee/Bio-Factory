package com.github.elenterius.biofactory.datagen.recipes;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.init.ModRecipes;
import com.github.elenterius.biomancy.init.ModItems;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class VanillaRecipeProvider extends RecipeProvider {

	protected VanillaRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	protected ResourceLocation getSpecialCraftingRecipeId(ItemLike itemLike) {
		return BioFactoryMod.createRL("special_crafting/" + getItemName(itemLike));
	}

	protected void special(Consumer<FinishedRecipe> consumer, ItemLike result, RecipeSerializer<? extends CraftingRecipe> serializer) {
		SpecialRecipeBuilder.special(serializer).save(consumer, getSpecialCraftingRecipeId(result).toString());
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		special(consumer, ModItems.ACOLYTE_ARMOR_HELMET.get(), ModRecipes.ACOLYTE_GOGGLES_UPGRADE_SERIALIZER.get());
	}

}
