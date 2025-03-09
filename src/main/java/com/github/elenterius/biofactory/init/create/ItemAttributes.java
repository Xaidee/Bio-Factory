package com.github.elenterius.biofactory.init.create;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import com.simibubi.create.content.logistics.item.filter.attribute.SingletonItemAttribute;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import java.util.function.Predicate;

public final class ItemAttributes {

	public static final ItemAttributeType ACID_CORRODIBLE = singleton("acid_corrodible", FanProcessingTypes.ACID_SPLASHING::canProcess);

	static void register() {
	}

	private static ItemAttributeType singleton(String id, Predicate<ItemStack> predicate) {
		return register(id, new SingletonItemAttribute.Type(type -> new SingletonItemAttribute(type, (stack, level) -> predicate.test(stack), id)));
	}

	private static ItemAttributeType register(String id, ItemAttributeType type) {
		return Registry.register(CreateBuiltInRegistries.ITEM_ATTRIBUTE_TYPE, BioFactoryMod.createRL(id), type);
	}

}
