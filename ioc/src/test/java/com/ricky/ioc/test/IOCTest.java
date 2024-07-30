package com.ricky.ioc.test;

import com.ricky.ioc.Container;
import com.ricky.ioc.annotation.Printable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className IOCTest
 * @desc
 */
public class IOCTest {

    private static final String CLASS_NAME = "com.ricky.ioc.model.Order";
    private static final String FIELD_NAME = "address";

    @Test
    public void init() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        // Given
        Container container = new Container();
        container.init("com.ricky.ioc.config.Config");
        Class<?> clazz = Class.forName(CLASS_NAME);

        // When
        Object obj = container.createInstance(clazz);
        Field field = clazz.getDeclaredField(FIELD_NAME);
        field.setAccessible(true);
        Object fieldValue = field.get(obj);

        Method[] methods = fieldValue.getClass().getDeclaredMethods();

        // Then
        // System.out.println(fieldValue);

        for (Method method : methods) {
            if(method.getDeclaredAnnotation(Printable.class) != null) {
                method.invoke(fieldValue);
            }
        }
    }

}
