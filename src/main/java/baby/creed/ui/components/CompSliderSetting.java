package baby.creed.ui.components;

import baby.creed.ui.ModernClickGui;
import baby.creed.ui.windows.ModuleWindow;
import baby.creed.util.MathUtil;
import baby.creed.util.font.Fonts;
import baby.creed.util.render.RenderUtils;
import baby.creed.Creed;
import baby.creed.settings.NumberSetting;

import java.awt.*;

public class CompSliderSetting extends Comp {
    public NumberSetting numberSetting;
    public CompSliderSetting(int x, int y, NumberSetting numberSetting)
    {
        this.x = x;
        this.y = y;
        this.numberSetting = numberSetting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, double scrollY)
    {
        double min = numberSetting.getMin();
        double max = numberSetting.getMax();
        double width = ModernClickGui.getWidth() - x - 5;

        double renderWidthMin = (width) * (numberSetting.getValue() - min) / (max - min);
        double diff = Math.min(width, Math.max(0, mouseX - (ModernClickGui.getX() + x)));

        if (ModuleWindow.selectedNumber != null && ModuleWindow.selectedNumber == numberSetting)
        {
            if (diff == 0)
            {
                numberSetting.setValue(numberSetting.getMin());
            }
            else
            {
                double newValue = MathUtil.round(((diff / width) * (max - min) + min), 2);
                numberSetting.setValue(newValue);
            }
        }

        RenderUtils.drawBorderedRoundedRect((float) (ModernClickGui.getX() + x), (float) (ModernClickGui.getY() + y + 13), (float) width, 2, 1, 2, Creed.themeManager.getSecondaryColor().getRGB(), Creed.themeManager.getSecondaryColor().getRGB());
        RenderUtils.drawBorderedRoundedRect((float) (ModernClickGui.getX() + x + renderWidthMin), (float) (ModernClickGui.getY() + y + 13), (float) (width - renderWidthMin), 2, 1, 1, Creed.themeManager.getSecondaryColor().getRGB(), Creed.themeManager.getSecondaryColor().getRGB());
        RenderUtils.drawBorderedRoundedRect((float) (ModernClickGui.getX() + x + renderWidthMin - 3), (float) (ModernClickGui.getY() + y + 10.75), 6, 6, 2.5f, 1, Creed.themeManager.getPrimaryColor().getRGB(), Creed.themeManager.getSecondaryColor().getRGB());

        Fonts.getPrimary().drawString(numberSetting.name + ": " + numberSetting.getValue(), ModernClickGui.getX() + x, ModernClickGui.getY() + y, Color.WHITE.getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (isHovered(mouseX, mouseY, ModernClickGui.getX() + x - 2, ModernClickGui.getY() + y + 10, ModernClickGui.getWidth() - x - 5, 10) && mouseButton == 0)
        {
            ModuleWindow.selectedNumber = numberSetting;
        }
    }
}
