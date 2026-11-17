package com.sarexstudio.sarexsafio4.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SarexPauseScreen extends Screen {
	private static final String NS = "sarexsafio4";
	private static final ResourceLocation STUDIO = tex("sarexstudio_logo");
	private static final ResourceLocation MAIN_LOGO = tex("sarexsafio4_logo");
	private static final ResourceLocation BTN_VOLVER = tex("volver_al_juego");
	private static final ResourceLocation BTN_OPCIONES = tex("opciones");
	private static final ResourceLocation BTN_SALIR = tex("desconectarse");

	// Tamaños reales (px) de cada textura
	private static final int STUDIO_W = 973, STUDIO_H = 293;
	private static final int MAIN_W = 1281, MAIN_H = 658;
	private static final int VOLVER_W = 936, VOLVER_H = 67;
	private static final int OPC_W = 860, OPC_H = 110;
	private static final int SALIR_W = 942, SALIR_H = 71;

	private int studioX, studioY, studioW, studioH;
	private int mainX, mainY, mainW, mainH;

	public SarexPauseScreen() {
		super(Component.translatable("menu.game"));
	}

	private static ResourceLocation tex(String name) {
		return ResourceLocation.fromNamespaceAndPath(NS, "textures/gui/" + name + ".png");
	}

	// ===== TAMAÑOS (fracción de la altura de la pantalla) - cambia estos números =====
	private static final double STUDIO_SCALE = 0.07;  // logo Sarex Studio
	private static final double MAIN_SCALE   = 0.28;  // logo Sarexsafio 4
	private static final double BUTTON_SCALE = 0.045; // alto de cada botón
	private static final double GAP_SCALE    = 0.025; // espacio entre botones
	// =================================================================================

	@Override
	protected void init() {
		int H = this.height, W = this.width;

		int studioH0 = Math.max(12, (int) (H * STUDIO_SCALE));
		int mainH0 = (int) (H * MAIN_SCALE);
		int bh = Math.max(10, (int) (H * BUTTON_SCALE));
		int gap = (int) (H * GAP_SCALE);
		int space = (int) (H * 0.03);

		studioH = studioH0;
		studioW = studioH * STUDIO_W / STUDIO_H;

		mainH = mainH0;
		mainW = mainH * MAIN_W / MAIN_H;
		if (mainW > W * 0.80) {
			mainW = (int) (W * 0.80);
			mainH = mainW * MAIN_H / MAIN_W;
		}

		// Todo el bloque centrado verticalmente
		int total = studioH + space / 2 + mainH + space + 3 * bh + 2 * gap;
		int top = Math.max(2, (H - total) / 2);

		studioY = top;
		studioX = (W - studioW) / 2;
		mainY = studioY + studioH + space / 2;
		mainX = (W - mainW) / 2;

		int y = mainY + mainH + space;
		addButton(BTN_VOLVER, VOLVER_W, VOLVER_H, bh, y, "Volver al juego", this::resume);
		y += bh + gap;
		addButton(BTN_OPCIONES, OPC_W, OPC_H, bh, y, "Opciones",
				() -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options)));
		y += bh + gap;
		addButton(BTN_SALIR, SALIR_W, SALIR_H, bh, y, "Desconectarse", this::disconnect);
	}

	private void addButton(ResourceLocation t, int tw, int th, int h, int y, String label, Runnable action) {
		int w = h * tw / th;
		if (w > this.width * 0.8) { w = (int) (this.width * 0.8); h = w * th / tw; }
		addRenderableWidget(new SarexImageButton((this.width - w) / 2, y, w, h, t, tw, th,
				Component.literal(label), action));
	}

	private void resume() {
		this.minecraft.setScreen(null);
		this.minecraft.mouseHandler.grabMouse();
	}

	private void disconnect() {
		Minecraft mc = this.minecraft;
		boolean local = mc.isLocalServer();
		if (mc.level != null) {
			mc.level.disconnect();
		}
		if (local) {
			mc.disconnect(new GenericMessageScreen(Component.translatable("menu.savingLevel")));
			mc.setScreen(new TitleScreen());
		} else {
			mc.disconnect();
			mc.setScreen(new JoinMultiplayerScreen(new TitleScreen()));
		}
	}

	@Override
	public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
		super.render(g, mouseX, mouseY, partialTick); // fondo + botones

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		g.blit(STUDIO, studioX, studioY, studioW, studioH, 0f, 0f, STUDIO_W, STUDIO_H, STUDIO_W, STUDIO_H);
		g.blit(MAIN_LOGO, mainX, mainY, mainW, mainH, 0f, 0f, MAIN_W, MAIN_H, MAIN_W, MAIN_H);
		RenderSystem.disableBlend();
	}
}
