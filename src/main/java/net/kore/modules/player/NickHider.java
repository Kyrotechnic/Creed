package net.kore.modules.player;

import net.kore.Kore;
import net.kore.modules.Module;
import net.kore.settings.StringSetting;

public class NickHider extends Module {
    public StringSetting nick;
    public NickHider()
    {
        super("Nick Hider", Module.Category.PLAYER);

        addSettings(
                nick = new StringSetting("Name", Kore.mc.getSession().getUsername())
        );
    }

    @Override
    public void assign()
    {
        Kore.nickHider = this;
    }

    @Override
    public String suffix()
    {
        return nick.getValue();
    }
}

