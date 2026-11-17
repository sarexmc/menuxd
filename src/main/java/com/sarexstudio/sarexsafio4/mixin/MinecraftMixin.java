package com.sarexstudio.sarexsafio4.mixin;

import com.sarexstudio.sarexsafio4.client.SarexPauseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	/** Cambia el menú de pausa de vanilla por el de Sarexsafio 4. */
	@ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
	private Screen sarex$replacePause(Screen screen) {
		if (screen instanceof PauseScreen pause && ((PauseScreenAccessor) pause).sarex$showPauseMenu()) {
			return new SarexPauseScreen();
		}
		return screen;
	}
}
