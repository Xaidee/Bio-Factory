package com.github.elenterius.biofactory.init.create;

import com.github.elenterius.biofactory.BioFactoryMod;
import com.github.elenterius.biomancy.init.AcidInteractions;
import com.github.elenterius.biomancy.init.ModBlocks;
import com.github.elenterius.biomancy.init.ModFluids;
import com.github.elenterius.biomancy.init.ModMobEffects;
import com.github.elenterius.biomancy.init.ModParticleTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingTypeRegistry;
import com.simibubi.create.foundation.utility.Color;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public final class FanProcessingTypes {

	public static final AcidSplashingType ACID_SPLASHING = register("acid_splashing", new AcidSplashingType());

	static void register() {
	}

	private static <T extends FanProcessingType> T register(String name, T type) {
		FanProcessingTypeRegistry.register(BioFactoryMod.createRL(name), type);
		return type;
	}

	public static class AcidSplashingType implements FanProcessingType {

		@Override
		public boolean isValidAt(Level level, BlockPos pos) {
			if (level.getFluidState(pos).getType().getFluidType() == ModFluids.ACID_TYPE.get()) {
				return true;
			}

			BlockState blockState = level.getBlockState(pos);
			if (blockState.is(ModBlocks.ACID_CAULDRON.get()) && ModBlocks.ACID_CAULDRON.get().isFull(blockState)) {
				return true;
			}

			return blockState.is(ModBlocks.ACID_FLUID_BLOCK.get());
		}

		@Override
		public int getPriority() {
			return 450;
		}

		@Override
		public boolean canProcess(ItemStack stack, Level level) {
			return canProcess(stack);
		}

		public boolean canProcess(ItemStack stack) {
			return stack.getItem() instanceof BlockItem blockItem && canProcess(blockItem.getBlock());
		}

		protected boolean canProcess(Block block) {
			if (block instanceof WeatheringCopper && WeatheringCopper.getNext(block).isPresent()) {
				return true;
			}
			return AcidInteractions.NORMAL_TO_ERODED_BLOCK_CONVERSION.containsKey(block);
		}

		@Override
		@Nullable
		public List<ItemStack> process(ItemStack stack, Level level) {
			if (stack.getItem() instanceof BlockItem blockItem) {
				return process(blockItem.getBlock())
					.map(Block::asItem).map(Item::getDefaultInstance).map(List::of)
					.orElse(null);
			}
			return null;
		}

		protected Optional<Block> process(Block block) {
			if (block instanceof WeatheringCopper && WeatheringCopper.getNext(block).isPresent()) {
				return Optional.of(WeatheringCopper.getNext(block).get());
			}
			return AcidInteractions.NORMAL_TO_ERODED_BLOCK_CONVERSION.containsKey(block) ? Optional.of((AcidInteractions.NORMAL_TO_ERODED_BLOCK_CONVERSION.get(block)).getBlock()) : Optional.empty();
		}

		@Override
		public void spawnProcessingParticles(Level level, Vec3 pos) {
			if (level.random.nextInt(8) != 0) {return;}
			Vector3f color = new Color(0x39ff14).asVectorF();
			level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
			level.addParticle(ParticleTypes.SPIT, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
		}

		@Override
		public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
			particleAccess.setColor(Color.mixColors(0x79ff53, 0x00d200, random.nextFloat()));
			particleAccess.setAlpha(1f);
			if (random.nextFloat() < 1 / 32f) {particleAccess.spawnExtraParticle(ModParticleTypes.ACID_BUBBLE.get(), .125f);}
			if (random.nextFloat() < 1 / 32f) {particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE_POP, .125f);}
		}

		@Override
		public void affectEntity(Entity entity, Level level) {
			if (level.isClientSide) {return;}

			if (entity instanceof LivingEntity livingEntity) {
				ModMobEffects.CORROSIVE.get().applyEffectTick(livingEntity, 0);
			}
		}

	}

}
