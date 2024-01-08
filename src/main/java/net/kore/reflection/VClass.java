package net.kore.reflection;

public class VClass {
    private final Class<?> localClass;
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

    public VMethod getMethod(String methodName, Class<?>... paramaterTypes) throws NoSuchMethodException {
        return new VMethod(localClass.getMethod(methodName, paramaterTypes));
    }
}
