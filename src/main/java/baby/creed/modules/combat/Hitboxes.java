package baby.creed.modules.combat;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;
import baby.creed.settings.NumberSetting;

public class Hitboxes extends Module
{
    public BooleanSetting onlyPlayers;
    public NumberSetting expand;

    public Hitboxes() {
        super("Hitboxes", Module.Category.COMBAT);
        this.onlyPlayers = new BooleanSetting("Only players", false);
        this.expand = new NumberSetting("Expand", 0.5, 0.1, 1.0, 0.1);
        this.addSettings(this.expand);

        this.flagRisky();
    }

    @Override
    public void assign()
    {
        Creed.hitboxes = this;
    }
}
