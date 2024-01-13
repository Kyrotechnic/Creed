package baby.creed.ui.windows;

import baby.creed.Creed;
import baby.creed.settings.Setting;
import baby.creed.ui.components.Comp;
import baby.creed.ui.components.CompModeSetting;

import java.util.ArrayList;
import java.util.List;

public class SettingsWindow extends Window {
    public List<Setting> settingList = new ArrayList<>();
    public SettingsWindow() {
        super("Settings");
        this.settingList.add(Creed.clickGui.colorMode);
        this.settingList.addAll(Creed.clientSettings.settings);
    }

    @Override
    public void initGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.drawScreen(mouseX, mouseY, (double)partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.mouseClicked(mouseX, mouseY, mouseButton);
        }

        Creed.themeManager.setTheme(Creed.clickGui.colorMode.getSelected());
        Creed.configManager.saveConfig();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (ModuleWindow.selectedString != null)
        {
            ModuleWindow.selectedString = null;
        }

        for (Comp comp : ModuleWindow.updateComps(this.settingList))
        {
            comp.keyTyped(typedChar, keyCode);
        }
    }
}