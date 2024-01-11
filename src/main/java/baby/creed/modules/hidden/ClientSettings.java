package baby.creed.modules.hidden;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;

public class ClientSettings extends Module {
    public BooleanSetting hideRiskyModules = new BooleanSetting("Hide Risky Modules", false);
    public ClientSettings()
    {
        super("", Category.HIDDEN);

        this.addSettings(hideRiskyModules);
    }

    @Override
    public void assign()
    {
        Creed.clientSettings = this;
    }
}
