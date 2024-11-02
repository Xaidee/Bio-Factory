package com.github.elenterius.biofactory.init.biomancy;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biomancy.init.ModItems;
import com.github.elenterius.biomancy.item.armor.AcolyteArmorItem;
import com.github.elenterius.biomancy.item.armor.AcolyteArmorUpgrades;
import com.github.elenterius.biomancy.item.armor.AcolyteArmorUpgrades.Upgrade;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public final class ArmorUpgrades {

	public static final Upgrade ACOLYTE_ENGINEERS_SIGHT_UPGRADE = AcolyteArmorUpgrades.register(BioFactoryMod.createRL("engineers_sight"));

	private ArmorUpgrades() {
	}

	static void register() {
		GogglesItem.addIsWearingPredicate(player -> {
			ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
			if (stack.getItem() instanceof AcolyteArmorItem armorItem && armorItem == ModItems.ACOLYTE_ARMOR_HELMET.get()) {
				return AcolyteArmorUpgrades.hasUpgrade(stack, ACOLYTE_ENGINEERS_SIGHT_UPGRADE) && armorItem.hasNutrients(stack);
			}
			return false;
		});
	}

}
