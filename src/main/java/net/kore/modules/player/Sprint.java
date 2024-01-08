package net.kore.modules.player;

import net.kore.Kore;
import net.kore.modules.Module;
import net.kore.settings.BooleanSetting;

public class Sprint extends Module
{
    public BooleanSetting omni;
    public BooleanSetting keep;

    public Sprint() {
        super("Sprint", 0, Category.PLAYER);
        this.omni = new BooleanSetting("OmniSprint", false);
        this.keep = new BooleanSetting("KeepSprint", true);
        this.addSettings(this.keep, this.omni);
    }

    @Override
    public void assign()
    {
        Kore.sprint = this;
    }
}
