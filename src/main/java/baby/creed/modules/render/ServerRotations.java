package baby.creed.modules.render;

import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;

public class ServerRotations extends Module
{
    private static ServerRotations instance;
    public BooleanSetting onlyKillAura;

    public static ServerRotations getInstance() {
        return ServerRotations.instance;
    }

    public ServerRotations() {
        super("Server Rotations", Category.RENDER);
        this.onlyKillAura = new BooleanSetting("Only aura rotations", false);
        this.setToggled(true);
        this.addSettings(this.onlyKillAura);
    }

    static {
        ServerRotations.instance = new ServerRotations();
    }
}

