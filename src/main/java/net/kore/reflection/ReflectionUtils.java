package net.kore.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class ReflectionUtils {
    private static final HashMap<Modifiers, Method> MODIFIER_METHOD_MAP = new HashMap<>();
    static
    {
        Class<?> clasz = Modifier.class;

        Method[] methods = clasz.getMethods();

        for (Modifiers mods : Modifiers.values())
        {
            MODIFIER_METHOD_MAP.put(mods, findMethodByNameLower("is" + mods.name(), methods));
        }
    }

    private static Method findMethodByNameLower(String name, Method[] methods)
    {
        String internalName = name.toLowerCase();

        for (Method method : methods)
        {
            if (method.getName().toLowerCase().equals(internalName) && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()) && method.getReturnType() == boolean.class)
            {
                return method;
            }
        }

        return null;
    }

    public static boolean isModifierPresent(Modifiers modifier, int mod)
    {
        Method method = MODIFIER_METHOD_MAP.get(modifier);

        try
        {
            return (boolean) method.invoke(null, mod);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
