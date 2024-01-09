package net.kore.modules.render;

import net.kore.Kore;
import net.kore.events.GuiChatEvent;
import net.kore.modules.Module;
import net.kore.settings.ModeSetting;
import net.kore.settings.NumberSetting;
import net.kore.ui.hud.DraggableComponent;
import net.kore.ui.hud.HudVec;
import net.kore.ui.hud.impl.InventoryHud;
import net.kore.util.StencilUtils;
import net.kore.util.font.Fonts;
import net.kore.util.render.BlurUtils;
import net.kore.util.render.RenderUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class InventoryDisplay extends Module
{
    public static ModeSetting defaultPosition;
    public NumberSetting x;
    public NumberSetting y;
    public ModeSetting blurStrength;

    public InventoryDisplay() {
        super("Inventory HUD", 0, Module.Category.RENDER);
        defaultPosition = new ModeSetting("Default Position", "Top Left", new String[] { "Top Left", "Top Right", "Bottom Left", "Bottom Right", "Custom"});
        this.x = new NumberSetting("X1234", 0.0, -100000.0, 100000.0, 1.0E-5, a -> true);
        this.y = new NumberSetting("Y1234", 0.0, -100000.0, 100000.0, 1.0E-5, a -> true);
        this.blurStrength = new ModeSetting("Blur Strength", "Low", new String[] { "None", "Low", "High" });
        this.addSettings(defaultPosition, this.x, this.y, this.blurStrength);
    }

    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (this.isToggled() && event.type.equals((Object)RenderGameOverlayEvent.ElementType.HOTBAR) && Kore.mc.thePlayer != null) {
            InventoryHud.inventoryHUD.drawScreen();
        }
    }

    @Override
    public void assign()
    {
        Kore.inventoryDisplay = this;
    }

    @SubscribeEvent
    public void onChatEvent(final GuiChatEvent event) {
        if (!this.isToggled()) {
            return;
        }

        final DraggableComponent component = InventoryHud.inventoryHUD;
        if (event instanceof GuiChatEvent.MouseClicked) {
            if (component.isHovered(event.mouseX, event.mouseY)) {
                defaultPosition.setSelected("Custom");
                component.startDragging();
            }
        }
        else if (event instanceof GuiChatEvent.MouseReleased) {
            component.stopDragging();
        }
        else if (event instanceof GuiChatEvent.Closed) {
            component.stopDragging();
        }
        else if (event instanceof GuiChatEvent.DrawChatEvent) {

        }
    }
}