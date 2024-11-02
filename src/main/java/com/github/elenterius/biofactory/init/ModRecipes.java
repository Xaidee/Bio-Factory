package com.github.elenterius.biofactory.init;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.crafting.AcolyteGogglesUpgradeRecipe;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipes {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BioFactoryMod.MOD_ID);
	//	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, BioFactoryMod.MOD_ID);

//	public static final RegistryObject<ItemStackRecipeType<DecomposerRecipe>> DECOMPOSING_RECIPE_TYPE = registerItemStackRecipeType("decomposing");
//	public static final RegistryObject<RecipeSerializer<DecomposerRecipe>> DECOMPOSING_SERIALIZER = registerRecipeSerializer(DECOMPOSING_RECIPE_TYPE, DecomposerRecipe.Serializer::new);

	// DYNAMIC
	public static final RegistryObject<RecipeSerializer<AcolyteGogglesUpgradeRecipe>> ACOLYTE_GOGGLES_UPGRADE_SERIALIZER = registerDynamicCraftingRecipeSerializer(RecipeType.CRAFTING,
		"acolyte_goggles_upgrade",
		() -> new SimpleCraftingRecipeSerializer<>(AcolyteGogglesUpgradeRecipe::new));

	private ModRecipes() {}

	private static <T extends RecipeType<?>, R extends Recipe<Container>> RegistryObject<RecipeSerializer<R>> registerRecipeSerializer(RegistryObject<T> recipeType, Supplier<RecipeSerializer<R>> serializerSupplier) {
		return RECIPE_SERIALIZERS.register(recipeType.getId().getPath(), serializerSupplier);
	}

	private static <T extends RecipeType<?>, R extends Recipe<Container>> RegistryObject<RecipeSerializer<R>> registerDynamicRecipeSerializer(RegistryObject<T> recipeType, String name, Supplier<RecipeSerializer<R>> serializerSupplier) {
		String prefix = recipeType.getId().getPath() + "_dynamic_";
		return RECIPE_SERIALIZERS.register(prefix + name, serializerSupplier);
	}

	private static <T extends CraftingRecipe, R extends CraftingRecipe> RegistryObject<RecipeSerializer<R>> registerDynamicCraftingRecipeSerializer(RecipeType<T> recipeType, String name, Supplier<RecipeSerializer<R>> serializerSupplier) {
		String prefix = Objects.requireNonNull(ResourceLocation.tryParse(recipeType.toString())).getPath() + "_dynamic_";
		return RECIPE_SERIALIZERS.register(prefix + name, serializerSupplier);
	}

	private static <R extends CraftingRecipe> RegistryObject<RecipeSerializer<R>> registerCraftingRecipeSerializer(String name, Supplier<RecipeSerializer<R>> serializer) {
		return RECIPE_SERIALIZERS.register(name, serializer);
	}

//	private static <T extends Recipe<Container>> RegistryObject<ItemStackRecipeType<T>> registerItemStackRecipeType(String namespacedId) {
//		return RECIPE_TYPES.register(namespacedId, () -> new ItemStackRecipeType<>(BiomancyMod.createRLString(namespacedId)));
//	}
//
//	private static <T extends Recipe<Container>> RegistryObject<SimpleRecipeType<T>> createSimpleRecipeType(String namespacedId) {
//		return RECIPE_TYPES.register(namespacedId, () -> new SimpleRecipeType<>(BiomancyMod.createRLString(namespacedId)));
//	}

}
