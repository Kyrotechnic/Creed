package baby.creed.util;

import baby.creed.Creed;
import net.minecraft.util.ChatComponentText;

public class ModUtils {
    public static void sendMessage(Object object) {
        if (Creed.mc.thePlayer == null)
        {
            return;
        }

        Creed.mc.thePlayer.addChatMessage(new ChatComponentText(Creed.fancy + "7[" + Creed.fancy + "q" + Creed.MOD_NAME + Creed.fancy + "7] " + Creed.fancy + "f" + object.toString().replaceAll("&", Creed.fancy + ""))); // §7[§cCreed§7] §f
    }
}