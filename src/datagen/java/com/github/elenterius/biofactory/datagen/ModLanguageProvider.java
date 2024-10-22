package com.github.elenterius.biofactory.datagen;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biofactory.init.ModItems;
import java.util.function.Supplier;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fluids.FluidType;

public class ModLanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

	public ModLanguageProvider(PackOutput output) {
		super(output, BioFactoryMod.MOD_ID, "en_us");
	}

	protected void add(Supplier<? extends CreativeModeTab> supplier, String name) {
		add(supplier.get(), name);
	}

	protected void add(CreativeModeTab tab, String name) {
		add(tab.getDisplayName(), name);
	}

	public void add(Component component, String translation) {
		if (!(component.getContents() instanceof TranslatableContents translatableContents)) {
			throw new IllegalArgumentException("Provided component does not contain translatable contents");
		}

		add(translatableContents.getKey(), translation);
	}

	protected void addFluidType(Supplier<? extends FluidType> supplier, String translation) {
		add(supplier.get(), translation);
	}

	protected void add(FluidType fluidType, String translation) {
		add(fluidType.getDescriptionId(), translation);
	}

	@Override
	protected void addTranslations() {
		add(BioFactoryMod.CREATIVE_TAB, "Bio-Factory");

		addItem(ModItems.NUTRIENTS_BOTTLE, "Bottle of Fluid Nutrients");
		addFluidType(ModFluids.NUTRIENTS_TYPE, "Nutrients");
	}

}
