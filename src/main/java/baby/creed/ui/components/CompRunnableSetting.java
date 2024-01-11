package baby.creed.ui.components;

import baby.creed.ui.ModernClickGui;
import baby.creed.util.font.Fonts;
import baby.creed.util.render.RenderUtils;
import baby.creed.Creed;
import baby.creed.settings.RunnableSetting;

import java.awt.*;

public class CompRunnableSetting extends Comp {
    public RunnableSetting runnableSetting;
    public CompRunnableSetting(int x, int y, RunnableSetting runnableSetting)
    {
        this.x = x;
        this.y = y;
        this.runnableSetting = runnableSetting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, double scrollY)
    {
        RenderUtils.drawBorderedRoundedRect((float) (ModernClickGui.getX() + x), (float) (ModernClickGui.getY() + y), (float) (ModernClickGui.getWidth() - x - 5), 15, 5, 1, Creed.themeManager.getPrimaryColor().getRGB(), Creed.themeManager.getSecondaryColor().getRGB());

        Fonts.getPrimary().drawCenteredString(runnableSetting.name, (float) (ModernClickGui.getX() + x + (ModernClickGui.getWidth() - x)/2), (float) (ModernClickGui.getY() + y + 3), Color.WHITE.getRGB());
    }
}
