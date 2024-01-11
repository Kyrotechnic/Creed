package baby.creed.util;

import baby.creed.Creed;
import net.minecraft.network.Packet;

public class PacketUtils {
    public static void sendPacket(Packet<?> packet)
    {
        Creed.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
}
