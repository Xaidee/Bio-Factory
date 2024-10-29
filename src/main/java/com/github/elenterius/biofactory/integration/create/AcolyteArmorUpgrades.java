package com.github.elenterius.biofactory.integration.create;

import com.github.elenterius.biomancy.init.ModItems;
import com.github.elenterius.biomancy.item.armor.AcolyteArmorItem;
import com.github.elenterius.biomancy.styles.TextComponentUtil;
import com.github.elenterius.biomancy.util.ComponentUtil;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public final class AcolyteArmorUpgrades {

	public static final String GOGGLES_UPGRADE_KEY = AllItems.GOGGLES.getId().toString();

	private AcolyteArmorUpgrades() {
	}

	static void register() {
		GogglesItem.addIsWearingPredicate(player -> {
			ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
			if (stack.getItem() instanceof AcolyteArmorItem armorItem && armorItem == ModItems.ACOLYTE_ARMOR_HELMET.get()) {
				CompoundTag tag = stack.getTag();
				return tag != null && tag.contains(GOGGLES_UPGRADE_KEY) && armorItem.hasNutrients(stack);
			}
			return false;
		});
	}

	public static ItemStack addGogglesUpgrade(ItemStack stack) {
		if (stack.is(ModItems.ACOLYTE_ARMOR_HELMET.get())) {
			stack.getOrCreateTag().putBoolean(GOGGLES_UPGRADE_KEY, true);
		}
		return stack;
	}

	public static boolean hasGogglesUpgrade(ItemStack stack) {
		if (stack.is(ModItems.ACOLYTE_ARMOR_HELMET.get())) {
			CompoundTag tag = stack.getTag();
			return tag != null && tag.contains(GOGGLES_UPGRADE_KEY);
		}
		return false;
	}

	public static void appendHoverText(ItemStack stack, List<Component> tooltip) {
		if (hasGogglesUpgrade(stack)) {
			tooltip.add(ComponentUtil.emptyLine());
			tooltip.add(TextComponentUtil.getAbilityText("engineers_sight").withStyle(ChatFormatting.GRAY));
			tooltip.add(ComponentUtil.literal(" ").append(TextComponentUtil.getAbilityText("engineers_sight.desc")).withStyle(ChatFormatting.DARK_GRAY));
		}
	}

}
