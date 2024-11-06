package com.github.elenterius.biofactory.item;

import com.github.elenterius.biofactory.init.biomancy.BiomancyIntegration;
import com.github.elenterius.biomancy.init.ModFoods;
import com.github.elenterius.biomancy.init.ModItems;
import com.github.elenterius.biomancy.util.MobUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class NutrientsBottleItem extends Item {

	public static final int FLUID_AMOUNT = 250;

	public NutrientsBottleItem(Properties properties) {
		super(properties.craftRemainder(Items.GLASS_BOTTLE).food(createFoodProperties()));
	}

	private static FoodProperties createFoodProperties() {
		float pct = (float) FLUID_AMOUNT / BiomancyIntegration.convertToFluidAmount(ModItems.NUTRIENT_BAR.get().getDefaultInstance());
		int nutrition = Mth.floor(pct * ModFoods.NUTRIENT_BAR.getNutrition());
		float saturation = pct * ModFoods.NUTRIENT_BAR.getSaturationModifier();
		return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation).alwaysEat().build();
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		super.finishUsingItem(stack, level, livingEntity);

		if (livingEntity instanceof ServerPlayer serverPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
			serverPlayer.awardStat(Stats.ITEM_USED.get(this));
		}

		if (!level.isClientSide) {
			livingEntity.removeEffect(MobEffects.HUNGER);
		}

		if (MobUtil.isCreativePlayer(livingEntity)) {return stack;}

		if (stack.isEmpty()) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}

		if (livingEntity instanceof Player player) {
			ItemStack itemStack = Items.GLASS_BOTTLE.getDefaultInstance();
			if (!player.getInventory().add(itemStack)) {
				player.drop(itemStack, false);
			}
		}

		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 42;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public SoundEvent getDrinkingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

}
