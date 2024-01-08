package net.kore.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class VMethod {
    private final Method localMethod;
    public VMethod(Method method)
    {
        this.localMethod = method;
    }

    public boolean isStatic()
    {
        return Modifier.isStatic(localMethod.getModifiers());
    }

    public boolean isPublic()
    {
        return Modifier.isPublic(localMethod.getModifiers());
    }

    public boolean isPrivate()
    {
        return !isPublic();
    }

    public boolean isAbstract()
    {
        return Modifier.isAbstract(localMethod.getModifiers());
    }

    public void setAccesible(boolean state)
    {
        localMethod.setAccessible(state);
    }

    public Object execute(Object... args)
    {
        return null;
    }
}
