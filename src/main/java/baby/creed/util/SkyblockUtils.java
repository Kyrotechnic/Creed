package baby.creed.util;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.realmsclient.gui.ChatFormatting;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import baby.creed.Creed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.scoreboard.*;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SkyblockUtils {
    public static boolean inDungeon;
    public static boolean isInOtherGame;
    public static boolean onSkyblock;
    public static boolean inBlood;
    public static boolean inP3;
    public static final String FAIRY_SOUL_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk2OTIzYWQyNDczMTAwMDdmNmFlNWQzMjZkODQ3YWQ1Mzg2NGNmMTZjMzU2NWExODFkYzhlNmIyMGJlMjM4NyJ9fX0=";
    public static String getDisplayName(final ItemStack item) {
        if (item == null) {
            return "null";
        }
        return item.getDisplayName();
    }

    public static boolean isFairySoul(EntityArmorStand entity)
    {
        ItemStack helmet = entity.getCurrentArmor(3);
        if (helmet == null || !(helmet.getItem() instanceof ItemSkull)) return false;

        NBTTagList tags = helmet.serializeNBT().getCompoundTag("tag").getCompoundTag("SkullOwner").getCompoundTag("Properties").getTagList("textures", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tags.tagCount(); i++)
        {
            if (tags.getCompoundTagAt(i).getString("Value").equals(FAIRY_SOUL_TEXTURE)) return true;
        }

        return false;
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Creed.mc.theWorld != null && event.phase == TickEvent.Phase.START) {
            SkyblockUtils.inDungeon = (hasLine("Cleared:") || hasLine("Start"));
            SkyblockUtils.isInOtherGame = isInOtherGame();
            SkyblockUtils.onSkyblock = isOnSkyBlock();
        }
    }

    public static boolean isInOtherGame() {
        try {
            final Scoreboard sb = Creed.mc.thePlayer.getWorldScoreboard();
            final List<Score> list = new ArrayList<Score>(sb.getSortedScores(sb.getObjectiveInDisplaySlot(1)));
            for (final Score score : list) {
                final ScorePlayerTeam team = sb.getPlayersTeam(score.getPlayerName());
                final String s = ChatFormatting.stripFormatting(ScorePlayerTeam.formatPlayerName((Team)team, score.getPlayerName()));
                if (s.contains("Map")) {
                    return true;
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }

    public static boolean hasLine(final String line) {
        if (Creed.mc.thePlayer != null && Creed.mc.thePlayer.getWorldScoreboard() != null && Creed.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) != null) {
            final Scoreboard sb = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
            final List<Score> list = new ArrayList<Score>(sb.getSortedScores(sb.getObjectiveInDisplaySlot(1)));
            for (final Score score : list) {
                final ScorePlayerTeam team = sb.getPlayersTeam(score.getPlayerName());
                if (team != null) {
                    final String s = ChatFormatting.stripFormatting(team.getColorPrefix() + score.getPlayerName() + team.getColorSuffix());
                    final StringBuilder builder = new StringBuilder();
                    for (final char c : s.toCharArray()) {
                        if (c < '\u0100') {
                            builder.append(c);
                        }
                    }
                    if (builder.toString().toLowerCase().contains(line.toLowerCase())) {
                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }

    public static boolean isOnSkyBlock() {
        try {
            final ScoreObjective titleObjective = Creed.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1);
            if (Creed.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0) != null) {
                return ChatFormatting.stripFormatting(Creed.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0).getDisplayName()).contains("SKYBLOCK");
            }
            return ChatFormatting.stripFormatting(Creed.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()).contains("SKYBLOCK");
        }
        catch (Exception e) {
            return false;
        }
    }

    public static String getSkyblockItemID(ItemStack item) {
        if (item == null) {
            return null;
        }

        NBTTagCompound extraAttributes = getExtraAttributes(item);
        if (extraAttributes == null) {
            return null;
        }

        if (!extraAttributes.hasKey("id", SkyblockUtils.NBT_STRING)) {
            return null;
        }

        return extraAttributes.getString("id");
    }

    public static final int NBT_INTEGER = 3;
    public static final int NBT_STRING = 8;
    public static final int NBT_LIST = 9;

    public static SkyblockArea currentArea = null;
    public static int ticks;
    public static Regex areaRegex = new Regex("^(?:Area|Dungeon): ([\\w ].+)\\$");

    public static Ordering<NetworkPlayerInfo> playerInfoOrdering = new Ordering<NetworkPlayerInfo>() {
        @Override
        public int compare(@Nullable NetworkPlayerInfo left, @Nullable NetworkPlayerInfo right) {
            ScorePlayerTeam team1 = left.getPlayerTeam();
            ScorePlayerTeam team2 = right.getPlayerTeam();

            if (team1 != null)
            {
                if (team2 != null)
                {
                    return ComparisonChain.start().compareTrueFirst(
                            left.getGameType() != WorldSettings.GameType.SPECTATOR,
                            right.getGameType() != WorldSettings.GameType.SPECTATOR
                    ).compare(
                            team1.getRegisteredName(),
                            team2.getRegisteredName()
                    ).compare(left.getGameProfile().getName(), right.getGameProfile().getName()).result();
                }
                return 0;
            }
            return -1;
        }
    };

    public static boolean isNPC(final Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
        return ChatFormatting.stripFormatting(entity.getDisplayName().getUnformattedText()).startsWith("[NPC]") || (entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0f && entityLivingBase.getMaxHealth() == 20.0f);
    }

    public static List<NetworkPlayerInfo> fetchTabList()
    {
        if (Creed.mc.thePlayer == null) return new ArrayList<>();
        return playerInfoOrdering.sortedCopy(Creed.mc.thePlayer.sendQueue.getPlayerInfoMap());
    }

    public static NBTTagCompound getExtraAttributes(ItemStack item) {
        if (item == null) {
            throw new NullPointerException("The item cannot be null!");
        }
        if (!item.hasTagCompound()) {
            return null;
        }

        return item.getSubCompound("ExtraAttributes", false);
    }

    public static boolean isTerminal(final String name) {
        return name.contains("Correct all the panes!") || name.contains("Navigate the maze!") || name.contains("Click in order!") || name.contains("What starts with:") || name.contains("Select all the") || name.contains("Change all to same color!") || name.contains("Click the button on time!");
    }


}

