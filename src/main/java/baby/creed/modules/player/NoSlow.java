package baby.creed.modules.player;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.ModeSetting;
import baby.creed.settings.NumberSetting;
import baby.creed.util.MilliTimer;

public class NoSlow extends Module {
    public NumberSetting eatingSlowdown;
    public NumberSetting swordSlowdown;
    public NumberSetting bowSlowdown;
    public ModeSetting mode;
    private final MilliTimer blockDelay;

    public NoSlow() {
        super("No Slow", 0, Category.PLAYER);
        this.eatingSlowdown = new NumberSetting("Eating slow", 1.0, 0.2, 1.0, 0.1);
        this.swordSlowdown = new NumberSetting("Sword slow", 1.0, 0.2, 1.0, 0.1);
        this.bowSlowdown = new NumberSetting("Bow slow", 1.0, 0.2, 1.0, 0.1);
        this.mode = new ModeSetting("Mode", "Vanilla", new String[] { "Vanilla" });
        this.blockDelay = new MilliTimer();
        this.addSettings(this.mode, this.swordSlowdown, this.bowSlowdown, this.eatingSlowdown);

        this.flagRisky();
    }

    @Override
    public void assign()
    {
        Creed.noSlow = this;
    }
}

