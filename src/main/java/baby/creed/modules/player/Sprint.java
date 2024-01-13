package baby.creed.modules.player;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;

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
    public FlagType getFlagType()
    {
        if (omni.isEnabled()) return FlagType.DETECTED;
        return FlagType.SAFE;
    }

    @Override
    public void assign()
    {
        Creed.sprint = this;
    }
}
