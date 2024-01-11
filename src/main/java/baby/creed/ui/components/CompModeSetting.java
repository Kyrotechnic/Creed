package baby.creed.ui.components;

import baby.creed.ui.ModernClickGui;
import baby.creed.util.font.Fonts;
import baby.creed.util.render.RenderUtils;
import baby.creed.Creed;
import baby.creed.settings.ModeSetting;

import java.awt.*;

public class CompModeSetting extends Comp {
    public ModeSetting modeSetting;
    public CompModeSetting(double x, double y, ModeSetting modeSetting)
    {
        this.x = x;
        this.y = y;
        this.modeSetting = modeSetting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, double scrollY)
    {
        RenderUtils.drawBorderedRoundedRect((float) (ModernClickGui.getX() + x), (float) (ModernClickGui.getY() + y), (float) (ModernClickGui.getWidth() - x - 5), 15, 5, 1, Creed.themeManager.getPrimaryColor().getRGB(), Creed.themeManager.getSecondaryColor().getRGB());

        String value = modeSetting.name + ": " + modeSetting.getSelected();

        Fonts.getPrimary().drawCenteredString(value, (float) (ModernClickGui.getX() + x + (ModernClickGui.getWidth() - x)/2), (float) (ModernClickGui.getY() + y + 3), Color.WHITE.getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isHovered(mouseX, mouseY, ModernClickGui.getX() + x, ModernClickGui.getY() + y, ModernClickGui.getWidth() - x - 5, 15)) {
            this.modeSetting.cycle(mouseButton);
        }
    }
}
