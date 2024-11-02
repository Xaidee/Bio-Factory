package com.github.elenterius.biofactory.mixin;

import com.github.elenterius.biofactory.init.create.AcolyteArmorUpgrades;
import com.github.elenterius.biomancy.item.armor.AcolyteArmorItem;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AcolyteArmorItem.class)
public abstract class AcolyteArmorItemMixin {

	@Inject(method = "appendHoverText", at = @At("HEAD"))
	private void onAppendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced, CallbackInfo ci) {
		AcolyteArmorUpgrades.appendHoverText(stack, tooltip);
	}

}
