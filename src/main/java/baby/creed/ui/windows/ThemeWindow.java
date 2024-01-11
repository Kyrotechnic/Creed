package baby.creed.ui.windows;

import baby.creed.Creed;
import baby.creed.ui.components.CompModeSetting;

public class ThemeWindow extends Window {
    public ThemeWindow() {
        super("Themes");
    }

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        CompModeSetting modeSetting = new CompModeSetting(95, 30, Creed.clickGui.colorMode);
        modeSetting.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        CompModeSetting modeSetting = new CompModeSetting(95, 30, Creed.clickGui.colorMode);
        modeSetting.mouseClicked(mouseX, mouseY, mouseButton);

        Creed.themeManager.setTheme(Creed.clickGui.colorMode.getSelected());
        Creed.configManager.saveConfig();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }
}
