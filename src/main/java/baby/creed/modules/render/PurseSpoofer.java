package baby.creed.modules.render;

import baby.creed.Creed;
import baby.creed.events.PacketReceivedEvent;
import baby.creed.modules.Module;
import baby.creed.settings.NumberSetting;
import baby.creed.util.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

public class PurseSpoofer extends Module {
    public NumberSetting additionalCoins = new NumberSetting("Coins", 0d, Double.MIN_VALUE, Double.MAX_VALUE, 0, aBoolean -> true);
    public PurseSpoofer()
    {
        super("Purse Spoofer", Category.RENDER);

        addSettings(additionalCoins);
    }

    @Override
    public void assign()
    {
        Creed.purseSpoofer = this;
    }

    @SubscribeEvent
    public void packet(PacketReceivedEvent event)
    {
        if (!isToggled() || Creed.mc.thePlayer == null || Creed.mc.theWorld == null) return;

        Packet<?> packet = event.packet;

        if (packet instanceof S3EPacketTeams)
        {
            S3EPacketTeams team = (S3EPacketTeams) packet;

            String strip = StringUtils.stripControlCodes(team.getPrefix()).toLowerCase();

            if (!strip.startsWith("purse: ")) return;

            double purseValue = Double.parseDouble(strip.split(" ")[1].replaceAll(",", ""));

            purseValue += additionalCoins.getValue();

            String newPurse = Creed.fancy + "fPurse: " + Creed.fancy + "6" + String.format("%,.1f", purseValue);

            System.out.println("found purse");

            try {
                Field field = S3EPacketTeams.class.getDeclaredField("prefix");

                field.setAccessible(true);

                field.set(team, newPurse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
