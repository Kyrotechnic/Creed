package net.kore.modules.player;

import net.kore.Kore;
import net.kore.events.MotionUpdateEvent;
import net.kore.events.PacketReceivedEvent;
import net.kore.modules.Module;
import net.kore.settings.ModeSetting;
import net.kore.settings.NumberSetting;
import net.kore.util.MilliTimer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Module {
    public NumberSetting eatingSlowdown;
    public NumberSetting swordSlowdown;
    public NumberSetting bowSlowdown;
    public ModeSetting mode;
    private final MilliTimer blockDelay;

    public NoSlow() {
        super("No Slow", 0, Category.PLAYER);
        this.eatingSlowdown = new NumberSetting("Eating slow", 1.0, 0.2, 1.0, 0.1);
        this.swordSlowdown = new NumberSetting("Sword slow", 1.0, 0.2, 1.0, 0.1);
        this.bowSlowdown = new NumberSetting("Bow slow", 1.0, 0.2, 1.0, 0.1);
        this.mode = new ModeSetting("Mode", "Vanilla", new String[] { "Vanilla" });
        this.blockDelay = new MilliTimer();
        this.addSettings(this.mode, this.swordSlowdown, this.bowSlowdown, this.eatingSlowdown);
    }

    @Override
    public void assign()
    {
        Kore.noSlow = this;
    }
}

