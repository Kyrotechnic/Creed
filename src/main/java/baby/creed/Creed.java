package baby.creed;

import baby.creed.managers.*;
import baby.creed.modules.Module;
import baby.creed.modules.combat.*;
import baby.creed.modules.hidden.ClientSettings;
import baby.creed.modules.misc.AutoExperiments;
import baby.creed.modules.misc.AutoHarp;
import baby.creed.modules.misc.GhostBlocks;
import baby.creed.modules.player.FastPlace;
import baby.creed.modules.player.GuiMove;
import baby.creed.modules.player.NoSlow;
import baby.creed.modules.player.Sprint;
import baby.creed.modules.protection.CustomBrand;
import baby.creed.modules.protection.ModHider;
import baby.creed.modules.protection.NickHider;
import baby.creed.modules.protection.Proxy;
import baby.creed.modules.render.*;
import baby.creed.util.Notification;
import baby.creed.util.font.Fonts;
import baby.creed.util.render.BlurUtils;
import com.google.common.collect.Lists;
import baby.creed.modules.render.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

@Mod(modid = Creed.MOD_ID, name = Creed.MOD_NAME, version = Creed.VERSION)
public class Creed {
    public static final String MOD_ID = "@ID@";
    public static final String MOD_NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    //Managers
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;
    public static ThemeManager themeManager;
    public static NotificationManager notificationManager;
    public static Minecraft mc;
    public static List<String> changelog;
    public static char fancy = (char) 167;


    //module dependencies
    public static ClientSettings clientSettings;
    public static Gui clickGui;
    public static ModHider modHider;
    public static AutoExperiments autoExperiments;
    public static AutoHarp autoHarp;
    public static baby.creed.modules.dev.Debug Debug;
    public static Proxy proxy;
    public static Giants giants;
    public static Hitboxes hitboxes;
    public static Aura aura;
    public static NoSlow noSlow;
    public static AntiBot antiBot;
    public static FreeCam freeCam;
    public static PopupAnimation popupAnimation;
    public static GuiMove guiMove;
    public static NickHider nickHider;
    public static Interfaces interfaces;
    public static Reach reach;
    public static AutoBlock autoBlock;
    public static Animations animations;
    public static CustomBrand customBrand;
    public static FastPlace fastPlace;
    public static InventoryDisplay inventoryDisplay;
    public static GhostBlocks ghostBlocks;
    public static Sprint sprint;
    public static void start()
    {
        Creed.mc = Minecraft.getMinecraft();

        moduleManager = new ModuleManager("baby.creed.modules");

        moduleManager.initReflection();

        configManager = new ConfigManager();

        themeManager = new ThemeManager();

        notificationManager = new NotificationManager();

        CommandManager.init();

        loadChangelog();

        for (Module module : moduleManager.modules)
        {
            MinecraftForge.EVENT_BUS.register(module);
        }

        BlurUtils.registerListener();
    }

    public static void handleKey(int key)
    {
        for (Module module : moduleManager.modules)
        {
            if (module.getKeycode() == key)
            {
                module.toggle();

                if (!clickGui.disableNotifs.isEnabled())
                    notificationManager.showNotification((module.isToggled() ? "Enabled" : "Disabled") + " " + module.getName(), 2000, Notification.NotificationType.INFO);
            }
        }
    }

    public static void loadChangelog()
    {
        changelog = Lists.newArrayList("This is a development build of Creed", "Use it and be aware we are not liable", "if you get banned.");
    }

    @Mod.EventHandler
    public void startForge(FMLPreInitializationEvent pre)
    {

    }

    @Mod.EventHandler
    public void startLate(FMLInitializationEvent event)
    {
        Fonts.bootstrap();

        start();
    }

    public static void sendMessage(String line)
    {
        mc.thePlayer.addChatMessage(new ChatComponentText(line));
    }
}
