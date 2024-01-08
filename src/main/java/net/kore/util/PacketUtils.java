package net.kore.util;

import net.kore.Kore;
import net.minecraft.network.Packet;

public class PacketUtils {
    public static void sendPacket(Packet<?> packet)
    {
        Kore.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
}
