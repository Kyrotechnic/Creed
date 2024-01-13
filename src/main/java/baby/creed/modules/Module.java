package baby.creed.modules;

import baby.creed.util.MilliTimer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import baby.creed.Creed;
import baby.creed.managers.ConfigManager;
import baby.creed.settings.Setting;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Module {
    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("toggled")
    private boolean toggled;
    @Expose
    @SerializedName("keyCode")
    private int keycode;
    private final Category category;
    public boolean extended;
    @Expose
    @SerializedName("settings")
    public ConfigManager.ConfigSetting[] cfgSettings;
    private boolean devOnly;
    public FlagType flagType;
    public final MilliTimer toggledTime;
    public final List<Setting> settings;

    public Module(final String name, final int keycode, final Category category) {
        this.toggledTime = new MilliTimer();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keycode = keycode;
        this.category = category;

        this.flagType = FlagType.SAFE;
    }

    public Module(final String name, final Category category) {
        this(name, 0, category);
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void toggle() {
        this.setToggled(!this.toggled);
    }

    public void onEnable() {
    }
    public void assign()
    {

    }

    public void flagRisky()
    {
        this.flagType = FlagType.DETECTED;
    }

    public FlagType getFlagType()
    {
        return this.flagType;
    }

    public void onSave() {
    }

    public String getSuffix()
    {
        return null;
    }

    public void addSetting(final Setting setting) {
        this.getSettings().add(setting);
    }

    public void addSettings(final Setting... settings) {
        for (final Setting setting : settings) {
            this.addSetting(setting);
        }
    }

    public Category getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPressed() {
        return this.keycode != 0 && Keyboard.isKeyDown(this.keycode);
    }

    public int getKeycode() {
        return this.keycode;
    }

    public void setKeycode(final int keycode) {
        this.keycode = keycode;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public static List<Module> getModulesByCategory(final Category c) {
        return (List<Module>) Creed.moduleManager.getModules().stream().filter(module -> module.category == c).collect(Collectors.toList());
    }

    /*public static <T> T getModule(final Class<T> module) {
        for (final Module m : Creed.modules) {
            if (m.getClass().equals(module)) {
                return (T)m;
            }
        }
        return null;
    }

    public static Module getModule(final Predicate<Module> predicate) {
        for (final Module m : Creed.modules) {
            if (predicate.test(m)) {
                return m;
            }
        }
        return null;
    }

    public static Module getModule(final String string) {
        for (final Module m : Creed.modules) {
            if (m.getName().equalsIgnoreCase(string)) {
                return m;
            }
        }
        return null;
    }*/

    public void setToggled(final boolean toggled) {
        if (this.toggled != toggled) {
            this.toggled = toggled;
            this.toggledTime.reset();
            if (toggled) {
                this.onEnable();
            }
            else {
                this.onDisable();
            }
        }
    }

    public String suffix()
    {
        return "";
    }

    public void onDisable() {
    }

    public void setDevOnly(final boolean devOnly) {
        this.devOnly = devOnly;
    }

    public boolean isDevOnly() {
        return this.devOnly;
    }

    protected static void sendMessage(final String message) {
        Creed.mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public enum Category
    {
        COMBAT("Combat"),
        RENDER("Render"),
        PLAYER("Player"),
        PROTECTIONS("Protections"),
        MISC("Misc"),
        SETTINGS("Settings");

        public String name;

        private Category(final String name) {
            this.name = name;
        }
    }

    public enum FlagType
    {
        SAFE,
        DETECTED
    }
}
