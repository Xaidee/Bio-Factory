package com.github.elenterius.biofactory.integration.create;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class ItemAttributes {

	public static final ItemAttribute ACID_CORRODIBLE = ItemAttribute.register(AcidCorrodibleAttribute.EMPTY);

	static void register() {
	}

	public static class AcidCorrodibleAttribute implements ItemAttribute {

		public static final AcidCorrodibleAttribute EMPTY = new AcidCorrodibleAttribute();

		@Override
		public boolean appliesTo(ItemStack stack, Level world) {
			return FanProcessingTypes.ACID_SPLASHING.canProcess(stack, world);
		}

		@Override
		public boolean appliesTo(ItemStack stack) {
			return FanProcessingTypes.ACID_SPLASHING.canProcess(stack);
		}

		@Override
		public List<ItemAttribute> listAttributesOf(ItemStack stack) {
			return FanProcessingTypes.ACID_SPLASHING.canProcess(stack) ? List.of(new AcidCorrodibleAttribute()) : List.of();
		}

		@Override
		public String getNBTKey() {
			return BioFactoryMod.createRLString("acid_corrodible");
		}

		@Override
		public String getTranslationKey() {
			return BioFactoryMod.MOD_ID + ".acid_corrodible";
		}

		@Override
		public void writeNBT(CompoundTag tag) {
		}

		@Override
		public ItemAttribute readNBT(CompoundTag tag) {
			return new AcidCorrodibleAttribute();
		}

	}

}
