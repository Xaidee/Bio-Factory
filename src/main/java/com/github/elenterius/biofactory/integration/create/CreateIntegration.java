package com.github.elenterius.biofactory.integration.create;

public final class CreateIntegration {

	private CreateIntegration() {
	}

	public static void onPostSetup() {
		InteractionBehaviors.register();
		EffectHandlers.register();
		FanProcessingTypes.register();
		ItemAttributes.register();
		AcolyteArmorUpgrades.register();
	}

}
