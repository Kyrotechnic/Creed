package baby.creed.modules.player;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.NumberSetting;

public class FastPlace extends Module {
    public NumberSetting placeDelay;
    public FastPlace()
    {
        super("Fast Place", Category.PLAYER);

        this.addSettings(this.placeDelay = new NumberSetting("Place delay", 2.0, 0.0, 4.0, 1.0));
    }

    @Override
    public void assign()
    {
        Creed.fastPlace = this;
    }
}
