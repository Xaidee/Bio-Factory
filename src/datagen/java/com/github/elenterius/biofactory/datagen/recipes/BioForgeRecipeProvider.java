package com.github.elenterius.biofactory.datagen.recipes;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.integration.create.AcolyteArmorUpgrades;
import com.github.elenterius.biomancy.api.nutrients.NutrientsContainerItem;
import com.github.elenterius.biomancy.datagen.recipes.builder.BioForgeRecipeBuilder;
import com.github.elenterius.biomancy.datagen.recipes.builder.ItemData;
import com.github.elenterius.biomancy.init.ModBioForgeTabs;
import com.github.elenterius.biomancy.init.ModItems;
import com.simibubi.create.AllItems;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

public class BioForgeRecipeProvider extends RecipeProvider {

	protected BioForgeRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	private static <T extends Item & NutrientsContainerItem> ItemStack withMaxNutrients(T item) {
		ItemStack itemStack = item.getDefaultInstance();
		item.setNutrients(itemStack, Integer.MAX_VALUE);
		return itemStack;
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		BioForgeRecipeBuilder.create(BioFactoryMod.MOD_ID, "acolyte_armor_helmet_upgrade", new ItemData(
				AcolyteArmorUpgrades.addGogglesUpgrade(withMaxNutrients(ModItems.ACOLYTE_ARMOR_HELMET.get()))
			))
			.addIngredient(StrictNBTIngredient.of(withMaxNutrients(ModItems.ACOLYTE_ARMOR_HELMET.get())))
			.addIngredient(AllItems.GOGGLES)
			.addIngredient(ModItems.ELASTIC_FIBERS.get(), 8)
			.addIngredient(ModItems.GEM_FRAGMENTS.get(), 4)
			.setCraftingCost(25)
			.setCategory(ModBioForgeTabs.TOOLS)
			.unlockedBy(ModItems.ACOLYTE_ARMOR_HELMET).save(consumer);
	}

}
