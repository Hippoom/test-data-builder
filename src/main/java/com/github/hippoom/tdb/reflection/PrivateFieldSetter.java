package com.github.hippoom.tdb.reflection;

import java.lang.reflect.Field;
import lombok.SneakyThrows;

public class PrivateFieldSetter {

    @SneakyThrows
    public static void set(Object target, String fieldName, Object value) {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
