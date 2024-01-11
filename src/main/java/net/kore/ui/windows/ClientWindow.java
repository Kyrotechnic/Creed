package net.kore.ui.windows;

import net.kore.Kore;
import net.kore.settings.Setting;
import net.kore.ui.components.Comp;

import java.util.List;

public class ClientWindow extends Window {
    public ClientWindow()
    {
        super("Client Settings");
    }
    @Override
    public void initGui()
    {
        this.settingList = Kore.clientSettings.settings;
    }

    public List<Setting> settingList;

    @Override
    public void drawScreen(int var1, int var2, float var3)
    {
        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.drawScreen(var1, var2, var3);
        }
    }

    @Override
    public void mouseClicked(int var1, int var2, int var3)
    {
        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.mouseClicked(var1, var2, var3);
        }
    }

    @Override
    public void mouseReleased(int var1, int var2, int var3)
    {
        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.mouseReleased(var1, var2, var3);
        }
    }

    @Override
    public void keyTyped(char var1, int var2)
    {
        if (ModuleWindow.selectedString != null)
        {
            ModuleWindow.selectedString = null;
        }

        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.keyTyped(var1, var2);
        }
    }
}
