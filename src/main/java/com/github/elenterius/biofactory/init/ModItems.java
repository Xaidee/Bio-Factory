package com.github.elenterius.biofactory.init;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biofactory.item.NutrientsBottleItem;
import com.github.elenterius.biomancy.api.serum.Serum;
import com.github.elenterius.biomancy.init.ModRarities;
import com.github.elenterius.biomancy.item.SerumItem;
import com.github.elenterius.biomancy.item.SimpleBlockItem;
import com.github.elenterius.biomancy.item.SimpleItem;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BioFactoryMod.MOD_ID);
//	public static final RegistryObject<EffectCureItem> NUTRIENT_BAR = registerItem("nutrient_bar", props -> new EffectCureItem(props.food(ModFoods.NUTRIENT_BAR)));
public static final RegistryObject<NutrientsBottleItem> NUTRIENTS_BOTTLE = registerItem("nutrients_fluid_bottle",
	properties -> new NutrientsBottleItem(properties.craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON)));

	private ModItems() {}

	public static <T extends Item> Stream<T> findItems(Class<T> clazz) {
		return ModItems.ITEMS.getEntries().stream()
				.map(RegistryObject::get)
				.filter(clazz::isInstance)
				.map(clazz::cast);
	}

	public static <T extends Item> Stream<RegistryObject<T>> findEntries(Class<T> clazz) {
		//noinspection unchecked
		return ModItems.ITEMS.getEntries().stream()
				.filter(registryObject -> clazz.isInstance(registryObject.get()))
				.map(registryObject -> (RegistryObject<T>) registryObject);
	}

	private static <T extends Item> RegistryObject<T> registerItem(String name, Function<Item.Properties, T> factory) {
		return ITEMS.register(name, () -> factory.apply(createProperties()));
	}

	private static <T extends Block> RegistryObject<SimpleBlockItem> registerSimpleBlockItem(RegistryObject<T> blockHolder) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> new SimpleBlockItem(blockHolder.get(), createProperties()));
	}

	private static <T extends Block> RegistryObject<SimpleBlockItem> registerSimpleBlockItem(RegistryObject<T> blockHolder, Rarity rarity) {
		return registerSimpleBlockItem(blockHolder, () -> createProperties().rarity(rarity));
	}

	private static <T extends Block> RegistryObject<SimpleBlockItem> registerSimpleBlockItem(RegistryObject<T> blockHolder, Supplier<Item.Properties> properties) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> new SimpleBlockItem(blockHolder.get(), properties.get()));
	}

	private static <T extends Block, I extends BlockItem> RegistryObject<I> registerBlockItem(RegistryObject<T> blockHolder, Function<T, I> factory) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> factory.apply(blockHolder.get()));
	}

	private static <T extends Block, I extends BlockItem> RegistryObject<I> registerBlockItem(RegistryObject<T> blockHolder, IBlockItemFactory<T, I> factory) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> factory.create(blockHolder.get(), createProperties()));
	}

	private static <T extends Block, I extends BlockItem> RegistryObject<I> registerBlockItem(RegistryObject<T> blockHolder, IBlockItemFactory<T, I> factory, Rarity rarity) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> factory.create(blockHolder.get(), createProperties().rarity(rarity)));
	}

	private static <T extends Serum> RegistryObject<SerumItem> registerSerumItem(RegistryObject<T> registryObject) {
		return ITEMS.register(registryObject.getId().getPath(), () -> new SerumItem(createProperties().stacksTo(16).rarity(ModRarities.UNCOMMON), registryObject));
	}

	private static RegistryObject<SimpleItem> registerSimpleVialItem(String name) {
		return ITEMS.register(name, () -> new SimpleItem(createProperties()));
	}

	private static RegistryObject<SimpleItem> registerSimpleItem(String name) {
		return ITEMS.register(name, () -> new SimpleItem(createProperties()));
	}

	private static RegistryObject<SimpleItem> registerSimpleItem(String name, Rarity rarity) {
		return registerSimpleItem(name, () -> createProperties().rarity(rarity));
	}

	private static Item.Properties createProperties() {
		return new Item.Properties().rarity(ModRarities.COMMON);
	}

	private static RegistryObject<SimpleItem> registerSimpleItem(String name, Supplier<Item.Properties> properties) {
		return ITEMS.register(name, () -> new SimpleItem(properties.get()));
	}

	interface IBlockItemFactory<T extends Block, I extends BlockItem> {
		I create(T block, Item.Properties properties);
	}

}
