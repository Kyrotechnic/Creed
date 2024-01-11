package baby.creed.modules.protection;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.ModeSetting;
import baby.creed.settings.StringSetting;

public class CustomBrand extends Module {
    public StringSetting brand = new StringSetting("Brand", "Forge");
    public ModeSetting forwardMethod = new ModeSetting("Forward Methoder", "Hide Forge", "Hide Forge", "Show Forge");
    public CustomBrand()
    {
        super("Custom Brand", Category.PROTECTIONS);

        addSettings(brand, forwardMethod);

        this.flagRisky();
    }

    @Override
    public void assign()
    {
        Creed.customBrand = this;
    }

    public boolean hideForge()
    {
        return this.forwardMethod.is("Hide Forge") && this.isToggled();
    }
}
