package baby.creed.themes.impl;

import baby.creed.themes.Theme;
import baby.creed.Creed;

import java.awt.*;

public class AstolfoTheme extends Theme {

    public AstolfoTheme()
    {
        super("Astolfo");
    }

    @Override
    public Color getPrimary()
    {
        return new Color(50, 50, 50);
    }

    @Override
    public Color getSecondary()
    {
        final float pos = (float)((Math.cos((System.currentTimeMillis() * Creed.clickGui.shiftSpeed.getValue()) / 1000.0) + 1.0) * 0.5);
        return Color.getHSBColor(0.5f + 0.4f * pos, 0.6f, 1.0f);
    }

    @Override
    public Color getSecondary(int index)
    {
        final float pos = (float)((Math.cos((index * 100 + System.currentTimeMillis() * Creed.clickGui.shiftSpeed.getValue()) / 1000.0) + 1.0) * 0.5);
        return Color.getHSBColor(0.5f + 0.4f * pos, 0.6f, 1.0f);
    }
}
