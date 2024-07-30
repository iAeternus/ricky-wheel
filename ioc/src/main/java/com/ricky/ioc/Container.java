package com.ricky.ioc;

import com.ricky.ioc.annotation.Autowired;
import com.ricky.ioc.annotation.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className Container
 * @desc
 */
public class Container {

    /**
     * 存储被@Bean注解修饰的方法
     * 键-方法返回值
     * 值-方法
     */
    private Map<Class<?>, Method> methods;

    /**
     * 配置Bean的配置类
     */
    private Object config;

    /**
     * 存储Bean对象，方便获取
     */
    private Map<Class<?>, Object> beans;

    /**
     * 初始化Container对象
     * @param configClassName 目标对象全类名
     */
    public void init(String configClassName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.methods = new HashMap<>();
        this.beans = new HashMap<>();

        Class<?> clazz = Class.forName(configClassName);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getDeclaredAnnotation(Bean.class) != null) {
                this.methods.put(method.getReturnType(), method);
            }
        }

        this.config = clazz.getConstructor().newInstance();
    }

    /**
     * 根据字节码获取Bean对象
     * @param clazz 目标字节码
     * @return Bean对象
     */
    public Object getBeanInstanceByClass(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        if(this.beans.containsKey(clazz)) {
            return this.beans.get(clazz);
        }

        if (this.methods.containsKey(clazz)) {
            Method method = this.methods.get(clazz);
            Object obj = method.invoke(this.config);
            this.beans.put(clazz, obj);
            return obj;
        }
        return null;
    }

    public Object createInstance(Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if(constructor.getDeclaredAnnotation(Autowired.class) != null) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] arguments = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    arguments[i] = getBeanInstanceByClass(parameterTypes[i]);
                }
                return constructor.newInstance(arguments);
            }
        }
        return clazz.getConstructor().newInstance();
    }

}
