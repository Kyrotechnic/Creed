package net.kore.mixins;

import net.kore.Kore;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {
    @Shadow protected abstract void renderStringAtPos(String text, boolean shadow);

    @Shadow public abstract int getStringWidth(String text);

    @Inject(method = "renderStringAtPos", at = @At("HEAD"), cancellable = true)
    public void render(final String text, final boolean shadow, CallbackInfo ci)
    {
        if (Kore.nickHider.isToggled() && text.contains(Kore.mc.getSession().getUsername()) && !Kore.mc.getSession().getUsername().equals(Kore.nickHider.nick.getValue()))
        {
            ci.cancel();
            this.renderStringAtPos(text.replaceAll(Kore.mc.getSession().getUsername(), Kore.nickHider.nick.getValue()), shadow);
        }
    }

    @Inject(method = { "getStringWidth" }, at = { @At("RETURN") }, cancellable = true)
    public void getWidth(String text, final CallbackInfoReturnable<Integer> cir)
    {
        if (text != null && Kore.nickHider.isToggled() && text.contains(Kore.mc.getSession().getUsername()) && !Kore.mc.getSession().getUsername().equals(Kore.nickHider.nick.getValue()))
        {
            cir.setReturnValue(this.getStringWidth(text.replaceAll(Kore.mc.getSession().getUsername(), Kore.nickHider.nick.getValue())));
        }
    }
}
