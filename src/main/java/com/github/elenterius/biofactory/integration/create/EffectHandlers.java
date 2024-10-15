package com.github.elenterius.biofactory.integration.create;

import com.github.elenterius.biofactory.init.ModFluids;
import com.github.elenterius.biomancy.util.CombatUtil;
import com.simibubi.create.content.fluids.OpenEndedPipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import java.util.List;

public final class EffectHandlers {

	private EffectHandlers(){ }

	static void register() {
		OpenEndedPipe.registerEffectHandler(new AcidEffectHandler());
		OpenEndedPipe.registerEffectHandler(new NutrientsEffectHandler());
	}

	private static class AcidEffectHandler implements OpenEndedPipe.IEffectHandler {

		@Override
		public boolean canApplyEffects(OpenEndedPipe openEndedPipe, FluidStack fluidStack) {
			return fluidStack.getFluid().getFluidType() == com.github.elenterius.biomancy.init.ModFluids.ACID_TYPE.get();
		}

		@Override
		public void applyEffects(OpenEndedPipe openEndedPipe, FluidStack fluidStack) {
			Level level = openEndedPipe.getWorld();
			if (level.getGameTime() % 5 != 0) {return;}

			List<LivingEntity> mobs = level.getEntitiesOfClass(LivingEntity.class, openEndedPipe.getAOE(), livingEntity -> !CombatUtil.hasAcidEffect(livingEntity));
			for (LivingEntity mob : mobs) {
				CombatUtil.applyAcidEffect(mob, 4);
			}

			BlockPos.betweenClosedStream(openEndedPipe.getAOE()).forEach(pos -> corrodeCopper(level, pos));
		}

		private void corrodeCopper(Level level, BlockPos pos) {
			if (level.random.nextFloat() >= 0.057f) {return;}

			BlockState blockState = level.getBlockState(pos);
			Block block = blockState.getBlock();
			if (block instanceof WeatheringCopper weatheringCopper && WeatheringCopper.getNext(block).isPresent()) {
				weatheringCopper.getNext(blockState).ifPresent(state -> level.setBlockAndUpdate(pos, state));
			}
		}

	}

	private static class NutrientsEffectHandler implements OpenEndedPipe.IEffectHandler {

		@Override
		public boolean canApplyEffects(OpenEndedPipe pipe, FluidStack fluid) {
			return fluid.getFluid().getFluidType() == ModFluids.NUTRIENTS_FLUID_GEL_TYPE.get();
		}

		@Override
		public void applyEffects(OpenEndedPipe pipe, FluidStack fluid) {
			Level level = pipe.getWorld();
			if (level.getGameTime() % 5 != 0) {return;}

			for (Player player : level.getEntitiesOfClass(Player.class, pipe.getAOE(), LivingEntity::isAlive)) {
				player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 7, 0, false, false, false));
			}
		}

	}

}
