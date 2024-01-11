package baby.creed.modules.dev;

import baby.creed.Creed;
import baby.creed.modules.Module;

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
}