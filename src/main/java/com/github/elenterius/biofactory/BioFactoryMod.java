package com.github.elenterius.biofactory;

import com.github.elenterius.biofactory.init.ModBlocks;
import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biofactory.init.ModItems;
import com.github.elenterius.biofactory.init.ModRecipes;
import com.github.elenterius.biomancy.api.livingtool.LivingTool;
import com.github.elenterius.biomancy.util.ComponentUtil;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(BioFactoryMod.MOD_ID)
public final class BioFactoryMod {

	public static final String MOD_ID = "biofactory";
	public static final Logger LOGGER = LogManager.getLogger("Bio-Factory");

	public BioFactoryMod() {
		GeckoLib.initialize();

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext modLoadingContext = ModLoadingContext.get();

		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);

		ModFluids.FLUID_TYPES.register(modEventBus);
		ModFluids.FLUIDS.register(modEventBus);

		ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

		CREATIVE_TABS.register(modEventBus);
	}

	public static ResourceLocation createRL(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static String createRLString(String path) {
		return MOD_ID + ":" + path;
	}

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BioFactoryMod.MOD_ID);
	public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
			.title(ComponentUtil.translatable("tab." + MOD_ID + ".main"))
		.icon(() -> new ItemStack(ModItems.NUTRIENTS_BOTTLE.get()))
			.displayItems((params, output) -> {
				Set<RegistryObject<? extends Item>> hiddenItems = Set.of();

				ModItems.ITEMS.getEntries().stream()
						.filter(entry -> !hiddenItems.contains(entry))
						.forEach(entry -> {
							Item item = entry.get();
							output.accept(item);

							if (item instanceof LivingTool livingTool) {
								ItemStack itemStack = item.getDefaultInstance();
								livingTool.setNutrients(itemStack, Integer.MAX_VALUE);
								output.accept(itemStack);
							}
						});
			})
			.build()
	);

}
