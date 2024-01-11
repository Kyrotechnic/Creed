package baby.creed.modules.render;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;
import baby.creed.settings.ModeSetting;
import baby.creed.settings.NumberSetting;

public class Animations extends Module
{
    public NumberSetting x;
    public NumberSetting y;
    public NumberSetting z;
    public NumberSetting size;
    public ModeSetting mode;
    public BooleanSetting showSwing;

    public Animations() {
        super("Animations", Module.Category.RENDER);
        this.x = new NumberSetting("x", 1.0, 0.01, 3.0, 0.02);
        this.y = new NumberSetting("y", 1.0, 0.01, 3.0, 0.02);
        this.z = new NumberSetting("z", 1.0, 0.01, 3.0, 0.02);
        this.size = new NumberSetting("size", 1.0, 0.01, 3.0, 0.02);
        this.mode = new ModeSetting("Mode", "1.7", new String[] { "1.7", "chill", "push", "spin", "vertical spin", "helicopter" });
        this.showSwing = new BooleanSetting("Swing progress", false);
        this.addSettings(this.x, this.y, this.z, this.size, this.mode, this.showSwing);
    }

    @Override
    public void assign()
    {
        Creed.animations = this;
    }
}
