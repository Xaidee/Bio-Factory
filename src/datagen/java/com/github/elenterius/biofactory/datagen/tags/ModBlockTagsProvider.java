package com.github.elenterius.biofactory.datagen.tags;

import com.github.elenterius.biofactory.BioFactoryMod;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

	public ModBlockTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper fileHelper) {
		super(output, lookupProvider, BioFactoryMod.MOD_ID, fileHelper);
	}

	protected IntrinsicTagAppender<Block> tag(String modId, String path) {
		return super.tag(createTagKey(modId, path));
	}

	protected TagKey<Block> createTagKey(String modId, String path) {
		return BlockTags.create(new ResourceLocation(modId, path));
	}

	@Override
	protected void addTags(Provider provider) {

	}

}
