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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class NutrientsBottleItem extends Item {

	public static final int FLUID_AMOUNT = 250;

	public NutrientsBottleItem(Properties properties) {
		super(properties);
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

		if (livingEntity instanceof Player player) {
			float pct = (float) FLUID_AMOUNT / BiomancyIntegration.convertTofluidAmount(ModItems.NUTRIENT_BAR.get().getDefaultInstance());
			int nutrition = Mth.floor(pct * ModFoods.NUTRIENT_BAR.getNutrition());
			float saturation = pct * ModFoods.NUTRIENT_BAR.getSaturationModifier();
			player.getFoodData().eat(nutrition, saturation);
		}

		if (MobUtil.isCreativePlayer(livingEntity)) {return stack;}

		stack.shrink(1);
		if (stack.isEmpty()) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}

		if (livingEntity instanceof Player player) {
			ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
			if (!player.getInventory().add(itemStack)) {
				player.drop(itemStack, false);
			}
		}

		livingEntity.gameEvent(GameEvent.EAT); //maybe should be DRINK instead, but only potions trigger that event
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

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

}
