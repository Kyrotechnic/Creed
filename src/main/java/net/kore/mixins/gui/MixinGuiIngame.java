package net.kore.mixins.gui;

import net.kore.Kore;
import net.kore.events.ScoreboardRenderEvent;
import net.kore.util.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {
    @Shadow
    public abstract FontRenderer getFontRenderer();

    @Inject(method = { "renderScoreboard" }, at = { @At("HEAD") }, cancellable = true)
    public void renderScoreboard(final ScoreObjective s, final ScaledResolution score, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new ScoreboardRenderEvent(s, score))) {
            ci.cancel();
        }
    }
}
