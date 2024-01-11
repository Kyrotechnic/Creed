package baby.creed.modules.render;

import baby.creed.ui.hud.impl.InventoryHud;
import baby.creed.Creed;
import baby.creed.events.GuiChatEvent;
import baby.creed.modules.Module;
import baby.creed.settings.ModeSetting;
import baby.creed.settings.NumberSetting;
import baby.creed.ui.hud.DraggableComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        if (this.isToggled() && event.type.equals((Object)RenderGameOverlayEvent.ElementType.HOTBAR) && Creed.mc.thePlayer != null) {
            InventoryHud.inventoryHUD.drawScreen();
        }
    }

    @Override
    public void assign()
    {
        Creed.inventoryDisplay = this;
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