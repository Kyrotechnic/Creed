package baby.creed.modules.combat;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.NumberSetting;

public class Reach extends Module
{
    public NumberSetting reach;
    public NumberSetting blockReach;

    public Reach() {
        super("Reach", 0, Category.COMBAT);
        this.reach = new NumberSetting("Range", 3.0, 2.0, 4.5, 0.1);
        this.blockReach = new NumberSetting("Block Range", 4.5, 2.0, 6.0, 0.01);
        this.addSettings(this.reach, this.blockReach);

        this.flagRisky();
    }

    @Override
    public void assign()
    {
        Creed.reach = this;
    }
}