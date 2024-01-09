package net.kore.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class VMethod {
    private final Method localMethod;
    private final VClass localClass;
    public VMethod(Method method, VClass clasz)
    {
        this.localMethod = method;
        this.localClass = clasz;
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

    public void setInstanceNew() throws Exception {
        localClass.instance();
    }

    public void setInstance(Object obj)
    {
        localClass.setInstance(obj);
    }

    public Object execute(Object... args) throws Exception
    {
        boolean isStatic = Modifier.isStatic(localMethod.getModifiers());

        if (isStatic)
        {
            return localMethod.invoke(null, args);
        }

        if (getInstance() == null)
        {
            throw new Exception("Method is not static and no instance is set!");
        }

        return localMethod.invoke(getInstance(), args);
    }

    public <T> Object executeAndCast(Object... args) throws Exception
    {
        return (T) this.execute(args);
    }

    public boolean is(Modifiers modifier)
    {
        return ReflectionUtils.isModifierPresent(modifier, localMethod.getModifiers());
    }

    private Class<?>[] getTypeArguments(Object... args)
    {
        Class<?>[] classTypes = new Class<?>[args.length];

        for (int i = 0; i < args.length; i++)
            classTypes[i] = args[i].getClass();

        return classTypes;
    }

    private Object getInstance()
    {
        try {
            return localClass.instance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
