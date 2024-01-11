package baby.creed.managers;

import baby.creed.ui.windows.*;
import baby.creed.modules.Module;
import baby.creed.ui.windows.*;

import java.util.ArrayList;
import java.util.List;

public class WindowManager {
    public List<Window> windows = new ArrayList<Window>();

    public WindowManager() {
        this.windows.add(new HomeWindow());
        for (Module.Category category : Module.Category.values()) {
            if (category == Module.Category.HIDDEN) continue;
            this.windows.add(new ModuleWindow(category));
        }

        this.windows.add(new ThemeWindow());
        this.windows.add(new ClientWindow());
    }

    public Window getDefaultWindow() {
        return this.windows.get(0);
    }
}