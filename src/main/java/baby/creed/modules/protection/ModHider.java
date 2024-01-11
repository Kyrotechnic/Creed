package baby.creed.modules.protection;

import baby.creed.Creed;
import baby.creed.modules.Module;

public class ModHider extends Module {
    public ModHider()
    {
        super("Mod Hider", Category.PROTECTIONS);

        setToggled(true);
    }

    @Override
    public void assign()
    {
        Creed.modHider = this;
    }
}
