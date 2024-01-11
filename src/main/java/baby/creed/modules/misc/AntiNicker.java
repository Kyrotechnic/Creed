package baby.creed.modules.misc;

import baby.creed.Creed;
import baby.creed.events.JoinGameEvent;
import baby.creed.modules.Module;
import baby.creed.util.SkyblockUtils;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class AntiNicker extends Module {
    public static ArrayList<UUID> nicked;

    public AntiNicker() {
        super("Anti Nicker", 0, Category.MISC);
    }

    public static String getRealName(final GameProfile profile) {
        final AtomicReference<String> toReturn = new AtomicReference<String>("");
        JsonParser parser;
        final AtomicReference<String> atomicReference = new AtomicReference<>();
        profile.getProperties().entries().forEach(entry -> {
            if (entry.getKey().equals("textures")) {
                try {
                    atomicReference.set(new JsonParser().parse(new String(Base64.getDecoder().decode(((Property)entry.getValue()).getValue()))).getAsJsonObject().get("profileName").getAsString());
                }
                catch (Exception ex) {}
            }
            return;
        });
        return atomicReference.get();
    }

    @SubscribeEvent
    public void onWorldJoin(final EntityJoinWorldEvent e) {
        if (!this.isToggled()) {
            return;
        }
        if (e.entity instanceof EntityOtherPlayerMP && !AntiNicker.nicked.contains(e.entity.getUniqueID()) && !e.entity.equals(Creed.mc.thePlayer) && !SkyblockUtils.isNPC(e.entity) && Creed.mc.getNetHandler().getPlayerInfo(e.entity.getUniqueID()) != null && !e.entity.getDisplayName().getUnformattedText().contains(ChatFormatting.RED.toString()) && (SkyblockUtils.onSkyblock || SkyblockUtils.isInOtherGame)) {
            final String realName = getRealName(((EntityPlayer)e.entity).getGameProfile());
            final String stipped = ChatFormatting.stripFormatting(e.entity.getName());
            if (stipped.equals(e.entity.getName()) && !realName.equals(stipped)) {
                AntiNicker.nicked.add(e.entity.getUniqueID());
                Creed.sendMessage((e.entity.getDisplayName().getUnformattedText().contains(ChatFormatting.OBFUSCATED.toString()) ? e.entity.getName() : e.entity.getDisplayName().getUnformattedText()) + ChatFormatting.RESET + ChatFormatting.GRAY + " is nicked!" + ((realName.equals("")) ? "" : (" Their real name is " + realName + "!")));
            }
        }
    }

    @SubscribeEvent
    public void onWorldJoin(final JoinGameEvent event) {
        AntiNicker.nicked.clear();
    }

    static {
        AntiNicker.nicked = new ArrayList<UUID>();
    }
}
