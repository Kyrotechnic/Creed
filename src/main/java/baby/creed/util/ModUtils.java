package baby.creed.util;

import baby.creed.Creed;
import net.minecraft.util.ChatComponentText;

public class ModUtils {
    public static void sendMessage(Object object) {
        String message = "null";
        if (object != null) {
            message = object.toString().replace("&", ""+ Creed.fancy);
        }
        if (Creed.mc.thePlayer != null) {
            Creed.mc.thePlayer.addChatMessage(new ChatComponentText(Creed.fancy + "7[" + Creed.fancy + "c" + Creed.MOD_NAME + Creed.fancy + "7] " + Creed.fancy + "f" + message)); // §7[§cCreed§7] §f
        }
    }
}