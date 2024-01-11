package baby.creed.themes.impl;

import baby.creed.themes.Theme;
import baby.creed.Creed;

import java.awt.*;

public class RainbowTheme extends Theme {
    public RainbowTheme()
    {
        super("Rainbow");
    }

    @Override
    public Color getSecondary()
    {
        return Color.getHSBColor((float) ((System.currentTimeMillis() * Creed.clickGui.rgbSpeed.getValue()) / 5000.0 % 1.0), 0.8f, 1.0f);
    }

    @Override
    public Color getSecondary(int index)
    {
        return Color.getHSBColor((float) ((index * 100 + System.currentTimeMillis() * Creed.clickGui.rgbSpeed.getValue()) / 5000.0 % 1.0), 0.8f, 1.0f);
    }
}
