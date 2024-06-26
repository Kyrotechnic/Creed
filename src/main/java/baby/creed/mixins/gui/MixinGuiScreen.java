package baby.creed.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin({ GuiScreen.class })
public abstract class MixinGuiScreen extends MixinGui {
    @Shadow protected List<GuiButton> buttonList;
    @Shadow
    public Minecraft mc;
    @Shadow
    public int height;
    @Shadow
    public int width;

    @Shadow
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }

    @Shadow
    public abstract void drawScreen(final int p0, final int p1, final float p2);
}
