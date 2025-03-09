package com.github.elenterius.biofactory.init.create;

import com.github.elenterius.biomancy.init.ModBlocks;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;

final class InteractionBehaviors {

	private InteractionBehaviors() {}

	static void register() {
		MovingInteractionBehaviour.REGISTRY.register(ModBlocks.FLESH_DOOR.get(), new FleshDoorMovingInteraction());
		MovingInteractionBehaviour.REGISTRY.register(ModBlocks.FULL_FLESH_DOOR.get(), new FullFleshDoorMovingInteraction());
		MovingInteractionBehaviour.REGISTRY.register(ModBlocks.FLESH_IRIS_DOOR.get(), new IrisDoorMovingInteraction());
	}

}
