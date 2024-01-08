package net.kore.modules.misc;

import net.kore.Kore;
import net.kore.modules.Module;
import net.kore.settings.StringSetting;

public class CustomBrand extends Module {
    public StringSetting brand = new StringSetting("Brand", "Forge");
    public CustomBrand()
    {
        super("Custom Brand", Category.MISC);

        addSettings(brand);
    }

    @Override
    public void assign()
    {
        Kore.customBrand = this;
    }
}
