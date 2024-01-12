package baby.creed.modules.dev;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.util.ModUtils;

public class Debug extends Module {
    public Debug()
    {
        super("Debug Mode", Category.DEV);

        setToggled(false);
    }

    @Override
    public void assign()
    {
        Creed.Debug = this;
    }

    @Override
    public void onEnable()
    {
        ModUtils.sendMessage("&qTest thing");
    }
}