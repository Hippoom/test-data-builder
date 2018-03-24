package com.github.hippoom.tdb.reflection;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {

    @SneakyThrows
    public static <T> T invoke(Object target, String methodName, Object... args) {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        //noinspection JavaReflectionInvocation,unchecked
        return (T) method.invoke(target, args);
    }
}
