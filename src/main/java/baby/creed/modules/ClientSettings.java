package baby.creed.modules;

import baby.creed.Creed;
import baby.creed.settings.BooleanSetting;

public class ClientSettings extends Module {
    public BooleanSetting hideDetectedModules = new BooleanSetting("Hide Detected", false);
    public BooleanSetting debug = new BooleanSetting("Debug Mode", false);
    public ClientSettings()
    {
        super("Settings", Category.SETTINGS);
        this.addSettings(hideDetectedModules, debug);
    }

    @Override
    public void assign()
    {
        Creed.clientSettings = this;
    }
}
