package baby.creed.util;

import baby.creed.Creed;
import net.minecraft.network.Packet;

import java.lang.reflect.Field;

public class PacketUtils {
    public static void sendPacket(Packet<?> packet)
    {
        Creed.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void logPacket(Packet<?> packet)
    {
        Field[] fields = packet.getClass().getDeclaredFields();

        System.out.println(packet.getClass().getTypeName());

        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);

                System.out.println(field.getName() + " -> " + field.get(packet));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
