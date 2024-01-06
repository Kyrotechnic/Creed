package net.kore.managers;

import net.kore.modules.Module;
import org.reflections.Reflections;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {
    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();
    public void init()
    {
        Reflections reflection = new Reflections("net.kore.modules");

        for (Class<? extends Module> module : reflection.getSubTypesOf(Module.class))
        {
            try {
                Module mod = module.getDeclaredConstructor().newInstance();

                modules.add(mod);

                mod.assign();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<Module> getModules()
    {
        return modules;
    }
}
