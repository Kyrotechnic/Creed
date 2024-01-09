package net.kore.ui;

import net.kore.events.GuiChatEvent;
import net.kore.ui.hud.DraggableComponent;
import net.kore.ui.windows.ModuleWindow;
import net.kore.ui.windows.Window;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;

public class DragGui extends GuiScreen {
    public DragGui()
    {

    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        MinecraftForge.EVENT_BUS.post(new GuiChatEvent.MouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void onGuiClosed()
    {
        MinecraftForge.EVENT_BUS.post(new GuiChatEvent.Closed());
    }

    @Override
    public void keyTyped(char c, int key)
    {
        MinecraftForge.EVENT_BUS.post(new GuiChatEvent.KeyTyped(key, c));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        MinecraftForge.EVENT_BUS.post(new GuiChatEvent.MouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float pt)
    {
        MinecraftForge.EVENT_BUS.post(new GuiChatEvent.DrawChatEvent(mouseX, mouseY));
    }
}
