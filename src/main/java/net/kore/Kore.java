package net.kore;

import com.google.common.collect.Lists;
import net.kore.managers.ConfigManager;
import net.kore.managers.ModuleManager;
import net.kore.managers.ThemeManager;
import net.kore.modules.Module;
import net.kore.modules.protection.ModHider;
import net.kore.modules.render.Gui;
import net.kore.util.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

@Mod(modid = Kore.MOD_ID, name = Kore.MOD_NAME, version = Kore.VERSION)
public class Kore {
    public static final String MOD_ID = "@ID@";
    public static final String MOD_NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    //Managers
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;
    public static ThemeManager themeManager;
    public static Minecraft mc;
    public static List<String> changelog;
    public static char fancy = (char) 167;


    //module dependencies
    public static Gui clickGui;
    public static ModHider modHider;
    public static void start()
    {
        Kore.mc = Minecraft.getMinecraft();

        moduleManager = new ModuleManager("net.kore.modules");

        moduleManager.initReflection();

        configManager = new ConfigManager();

        themeManager = new ThemeManager();

        loadChangelog();
    }

    public static void handleKey(int key)
    {
        for (Module module : moduleManager.modules)
        {
            if (module.getKeycode() == key)
            {
                module.toggle();
            }
        }
    }

    public static void loadChangelog()
    {
        changelog = Lists.newArrayList("This is a development build of Kore", "Use it and be aware we are not liable", "if you get banned.");
    }

    @Mod.EventHandler
    public void startForge(FMLPreInitializationEvent pre)
    {

    }

    @Mod.EventHandler
    public void startLate(FMLInitializationEvent event)
    {
        start();

        Fonts.bootstrap();
    }
}
