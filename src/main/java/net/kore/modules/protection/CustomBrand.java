package net.kore.modules.protection;

import net.kore.Kore;
import net.kore.modules.Module;
import net.kore.settings.ModeSetting;
import net.kore.settings.StringSetting;

public class CustomBrand extends Module {
    public StringSetting brand = new StringSetting("Brand", "Forge");
    public ModeSetting forwardMethod = new ModeSetting("Forward Methoder", "Hide Forge", "Hide Forge", "Show Forge");
    public CustomBrand()
    {
        super("Custom Brand", Category.PROTECTIONS);

        addSettings(brand, forwardMethod);
    }

    @Override
    public void assign()
    {
        Kore.customBrand = this;
    }

    public boolean hideForge()
    {
        return this.forwardMethod.is("Hide Forge") && this.isToggled();
    }
}
