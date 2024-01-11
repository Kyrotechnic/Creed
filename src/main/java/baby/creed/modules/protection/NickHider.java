package baby.creed.modules.protection;

import baby.creed.Creed;
import baby.creed.modules.Module;
import baby.creed.settings.StringSetting;

public class NickHider extends Module {
    public StringSetting nick;
    public NickHider()
    {
        super("Nick Hider", Category.PROTECTIONS);

        addSettings(
                nick = new StringSetting("Name", Creed.mc.getSession().getUsername())
        );
    }

    @Override
    public void assign()
    {
        Creed.nickHider = this;
    }

    @Override
    public String suffix()
    {
        return nick.getValue();
    }
}

