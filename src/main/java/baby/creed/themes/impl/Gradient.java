package baby.creed.themes.impl;

import baby.creed.themes.Theme;
import baby.creed.Creed;

import java.awt.*;

public class Gradient extends Theme {
    public Gradient()
    {
        super("Gradient");
    }

    @Override
    public Color getSecondary()
    {
        final float location = (float)((Math.cos((System.currentTimeMillis() * Creed.clickGui.shiftSpeed.getValue()) / 1000.0) + 1.0) * 0.5);
        if (!Creed.clickGui.hsb.isEnabled()) {
            return new Color((int)(Creed.clickGui.redShift1.getValue() + (Creed.clickGui.redShift2.getValue() - Creed.clickGui.redShift1.getValue()) * location), (int)(Creed.clickGui.greenShift1.getValue() + (Creed.clickGui.greenShift2.getValue() - Creed.clickGui.greenShift1.getValue()) * location), (int)(Creed.clickGui.blueShift1.getValue() + (Creed.clickGui.blueShift2.getValue() - Creed.clickGui.blueShift1.getValue()) * location));
        }
        final float[] c1 = Color.RGBtoHSB((int)Creed.clickGui.redShift1.getValue(), (int)Creed.clickGui.greenShift1.getValue(), (int)Creed.clickGui.blueShift1.getValue(), null);
        final float[] c2 = Color.RGBtoHSB((int)Creed.clickGui.redShift2.getValue(), (int)Creed.clickGui.greenShift2.getValue(), (int)Creed.clickGui.blueShift2.getValue(), null);
        return Color.getHSBColor(c1[0] + (c2[0] - c1[0]) * location, c1[1] + (c2[1] - c1[1]) * location, c1[2] + (c2[2] - c1[2]) * location);
    }

    @Override
    public Color getSecondary(int index)
    {
        final float location = (float)((Math.cos((index * 100 + System.currentTimeMillis() * Creed.clickGui.shiftSpeed.getValue()) / 1000.0) + 1.0) * 0.5);
        if (!Creed.clickGui.hsb.isEnabled()) {
            return new Color((int)(Creed.clickGui.redShift1.getValue() + (Creed.clickGui.redShift2.getValue() - Creed.clickGui.redShift1.getValue()) * location), (int)(Creed.clickGui.greenShift1.getValue() + (Creed.clickGui.greenShift2.getValue() - Creed.clickGui.greenShift1.getValue()) * location), (int)(Creed.clickGui.blueShift1.getValue() + (Creed.clickGui.blueShift2.getValue() - Creed.clickGui.blueShift1.getValue()) * location));
        }
        final float[] c1 = Color.RGBtoHSB((int)Creed.clickGui.redShift1.getValue(), (int)Creed.clickGui.greenShift1.getValue(), (int)Creed.clickGui.blueShift1.getValue(), null);
        final float[] c2 = Color.RGBtoHSB((int)Creed.clickGui.redShift2.getValue(), (int)Creed.clickGui.greenShift2.getValue(), (int)Creed.clickGui.blueShift2.getValue(), null);
        return Color.getHSBColor(c1[0] + (c2[0] - c1[0]) * location, c1[1] + (c2[1] - c1[1]) * location, c1[2] + (c2[2] - c1[2]) * location);
    }
}
