package baby.creed.managers;

import baby.creed.ui.windows.*;
import baby.creed.modules.Module;

import java.util.ArrayList;
import java.util.List;

public class WindowManager {
    public List<Window> windows = new ArrayList<Window>();

    public WindowManager() {
        this.windows.add(new HomeWindow());
        for (Module.Category category : Module.Category.values()) {
            if (category == Module.Category.SETTINGS) continue;
            this.windows.add(new ModuleWindow(category));
        }

        this.windows.add(new SettingsWindow());
    }

    public Window getDefaultWindow() {
        return this.windows.get(0);
    }
}
