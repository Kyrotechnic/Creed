package baby.creed.modules.render;

import baby.creed.Creed;
import baby.creed.events.PacketReceivedEvent;
import baby.creed.modules.Module;
import baby.creed.settings.NumberSetting;
import baby.creed.util.ModUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

public class PurseSpoofer extends Module {
    public NumberSetting additionalCoins = new NumberSetting("Coins", 1000d, Double.MIN_VALUE + 1, Double.MAX_VALUE - 1, 0, aBoolean -> true)
    {
        @Override
        public void setValue(double value)
        {
            this.value = value;
        }
    };
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

    @Override
    public void onEnable() {
        ModUtils.sendMessage("(PurseSpoofer) Usage -> .setcoins <value>");
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

            final double purseValue = Double.parseDouble(strip.split(" ")[1].replaceAll(",", ""));
            final double addCoins = additionalCoins.getValue();

            String newPurse = Creed.fancy + "fPurse: " + Creed.fancy + "6" + String.format("%,.1f", ((float)purseValue + (float)addCoins));
            if(Creed.Debug.isToggled()) {
                System.out.println("found purse. New purse is " + newPurse);
                System.out.println("Values are purse: " + purseValue + " and add is " + addCoins);
            }

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
