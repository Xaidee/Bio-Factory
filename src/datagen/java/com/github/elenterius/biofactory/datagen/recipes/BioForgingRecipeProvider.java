package com.github.elenterius.biofactory.datagen.recipes;

import com.github.elenterius.biomancy.api.nutrients.NutrientsContainerItem;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BioForgingRecipeProvider extends RecipeProvider {

	protected BioForgingRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	private static <T extends Item & NutrientsContainerItem> ItemStack withMaxNutrients(T item) {
		ItemStack itemStack = item.getDefaultInstance();
		item.setNutrients(itemStack, Integer.MAX_VALUE);
		return itemStack;
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		//		BioForgingRecipeBuilder.create(BioFactoryMod.MOD_ID, "acolyte_armor_helmet_upgrade", new ItemData(
		//				AcolyteArmorUpgrades.addUpgrade(withMaxNutrients(ModItems.ACOLYTE_ARMOR_HELMET.get()), ArmorUpgrades.ACOLYTE_ENGINEERS_SIGHT_UPGRADE)
		//			))
		//			.addIngredient(StrictNBTIngredient.of(withMaxNutrients(ModItems.ACOLYTE_ARMOR_HELMET.get())))
		//			.addIngredient(AllItems.GOGGLES)
		//			.addIngredient(ModItems.ELASTIC_FIBERS.get(), 8)
		//			.addIngredient(ModItems.GEM_FRAGMENTS.get(), 4)
		//			.setCraftingCost(25)
		//			.setCategory(ModBioForgeTabs.TOOLS)
		//			.unlockedBy(ModItems.ACOLYTE_ARMOR_HELMET).save(consumer);
	}

}
