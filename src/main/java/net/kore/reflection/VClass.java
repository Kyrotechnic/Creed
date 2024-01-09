package net.kore.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class VClass {
    private final Class<?> localClass;
    private Object instance = null;
    public VClass(String classPath) throws ClassNotFoundException {
        this.localClass = Class.forName(classPath);
    }

    public VClass(Class<?> clasz)
    {
        localClass = clasz;
    }

    public Class<?> getLoadedClass()
    {
        return localClass;
    }

    public VMethod getMethod(String methodName, Class<?>... paramaterTypes) throws Exception {
        return new VMethod(localClass.getMethod(methodName, paramaterTypes), this);
    }

    public Object instance() throws Exception {
        if (instance == null)
        {
            return instance = localClass.newInstance();
        }

        return instance;
    }

    public Object instance(Object... args) throws Exception {
        Class<?>[] typeParameters = this.getTypeArguments(args);

        return instance = localClass.getDeclaredConstructor(typeParameters).newInstance(args);
    }

    public void setInstance(Object obj)
    {
        instance = obj;
    }

    private Class<?>[] getTypeArguments(Object... args)
    {
        Class<?>[] classTypes = new Class<?>[args.length];

        for (int i = 0; i < args.length; i++)
            classTypes[i] = args[i].getClass();

        return classTypes;
    }
}
