package com.github.elenterius.biofactory.crafting;

import com.github.elenterius.biofactory.init.ModRecipes;
import com.github.elenterius.biofactory.init.biomancy.ArmorUpgrades;
import com.github.elenterius.biomancy.init.ModItems;
import com.github.elenterius.biomancy.item.armor.AcolyteArmorUpgrades;
import com.simibubi.create.AllItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class AcolyteGogglesUpgradeRecipe extends CustomRecipe {

	public AcolyteGogglesUpgradeRecipe(ResourceLocation id, CraftingBookCategory category) {
		super(id, category);
	}

	@Override
	public boolean matches(CraftingContainer inventory, Level level) {
		boolean hasHelmet = false;
		boolean hasPrimordialCore = false;

		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);

			if (stack.isEmpty()) {continue;}
			Item item = stack.getItem();

			if (item == ModItems.ACOLYTE_ARMOR_HELMET.get()) {
				if (hasHelmet) {return false;}
				if (AcolyteArmorUpgrades.hasUpgrade(stack, ArmorUpgrades.ACOLYTE_ENGINEERS_SIGHT_UPGRADE)) {return false;}
				hasHelmet = true;
			}
			else if (item == AllItems.GOGGLES.get()) {
				if (hasPrimordialCore) {return false;}
				hasPrimordialCore = true;
			}
			else {return false;}
		}

		return hasHelmet && hasPrimordialCore;
	}

	@Override
	public ItemStack assemble(CraftingContainer inventory, RegistryAccess registryAccess) {
		ItemStack helmet = ItemStack.EMPTY;
		boolean hasPrimordialCore = false;

		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);

			if (stack.isEmpty()) {continue;}
			Item item = stack.getItem();

			if (item == ModItems.ACOLYTE_ARMOR_HELMET.get()) {
				if (!helmet.isEmpty()) {return ItemStack.EMPTY;}
				if (AcolyteArmorUpgrades.hasUpgrade(stack, ArmorUpgrades.ACOLYTE_ENGINEERS_SIGHT_UPGRADE)) {return ItemStack.EMPTY;}
				helmet = stack;
			}
			else if (item == AllItems.GOGGLES.get()) {
				if (hasPrimordialCore) {return ItemStack.EMPTY;}
				hasPrimordialCore = true;
			}
			else {return ItemStack.EMPTY;}
		}

		return hasPrimordialCore && !helmet.isEmpty() ? AcolyteArmorUpgrades.addUpgrade(helmet.copy(), ArmorUpgrades.ACOLYTE_ENGINEERS_SIGHT_UPGRADE) : ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.ACOLYTE_GOGGLES_UPGRADE_SERIALIZER.get();
	}

}