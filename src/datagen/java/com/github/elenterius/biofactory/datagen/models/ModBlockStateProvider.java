package com.github.elenterius.biofactory.datagen.models;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.Objects;
import java.util.function.Supplier;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, BioFactoryMod.MOD_ID, fileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		particleOnly(ModBlocks.NUTRIENTS_FLUID_GEL);
	}

	protected ResourceLocation registryKey(Block block) {
		return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
	}

	protected String path(Block block) {
		return registryKey(block).getPath();
	}

	protected <T extends Block> void particleOnly(RegistryObject<T> block) {
		particleOnly(block.get());
	}

	protected <T extends Block> void particleOnly(Supplier<T> block, ResourceLocation particleTexture) {
		particleOnly(block.get(), particleTexture);
	}

	protected void particleOnly(Block block, ResourceLocation particleTexture) {
		String path = path(block);
		simpleBlock(block, models().getBuilder(path).texture("particle", particleTexture));
	}

	protected void particleOnly(Block block) {
		ResourceLocation key = registryKey(block);
		String path = key.getPath();
		simpleBlock(block, models().getBuilder(path).texture("particle", key.withPrefix("block/")));
	}

}
