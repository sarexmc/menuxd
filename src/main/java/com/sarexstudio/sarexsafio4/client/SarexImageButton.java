package com.sarexstudio.sarexsafio4.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SarexImageButton extends AbstractWidget {
	private final ResourceLocation texture;
	private final int texW, texH;
	private final Runnable action;

	public SarexImageButton(int x, int y, int w, int h, ResourceLocation texture, int texW, int texH,
							Component label, Runnable action) {
		super(x, y, w, h, label);
		this.texture = texture;
		this.texW = texW;
		this.texH = texH;
		this.action = action;
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		action.run();
	}

	@Override
	protected void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
		boolean hot = this.isHoveredOrFocused();
		float scale = hot ? 1.10f : 1.0f;
		float shade = hot ? 1.0f : 0.85f;

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderColor(shade, shade, shade, this.alpha);
		g.pose().pushPose();
		g.pose().translate(getX() + width / 2f, getY() + height / 2f, 0);
		g.pose().scale(scale, scale, 1f);
		g.pose().translate(-width / 2f, -height / 2f, 0);
		g.blit(texture, 0, 0, width, height, 0f, 0f, texW, texH, texW, texH);
		g.pose().popPose();
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.disableBlend();
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput output) {
		this.defaultButtonNarrationText(output);
	}
}
