package net.kore.modules.hidden;

import net.kore.Kore;
import net.kore.modules.Module;
import net.kore.settings.BooleanSetting;

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
        Kore.clientSettings = this;
    }
}
