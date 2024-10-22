package com.github.elenterius.biofactory.datagen.models;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.init.ModItems;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, BioFactoryMod.MOD_ID, fileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(ModItems.NUTRIENTS_BOTTLE.get());
	}

	private ResourceLocation registryKey(Item item) {
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
	}

	public ItemModelBuilder dynamicBucket(BucketItem item) {
		ResourceLocation itemKey = registryKey(item);
		ResourceLocation fluidKey = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(item.getFluid()));
		ResourceLocation loaderKey = new ResourceLocation("forge", "fluid_container");
		ResourceLocation bucketModelKey = new ResourceLocation("forge", "item/bucket");

		return getBuilder(itemKey.toString())
			.parent(getExistingFile(bucketModelKey))
			.customLoader((builder, existingFileHelper) -> new CustomLoaderBuilder<ItemModelBuilder>(loaderKey, builder, existingFileHelper) {
				@Override
				public JsonObject toJson(JsonObject json) {
					json = super.toJson(json);
					json.addProperty("fluid", fluidKey.toString());
					return json;
				}
			}).end();
	}

}
