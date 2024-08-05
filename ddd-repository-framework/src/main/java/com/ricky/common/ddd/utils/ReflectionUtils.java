package com.ricky.common.ddd.utils;

import com.ricky.common.ddd.marker.Aggregate;
import com.ricky.common.ddd.marker.Identifier;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/6/18
 * @className ReflectionUtils
 * @desc
 */
@Slf4j
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static <T extends Aggregate<ID>, ID extends Identifier> void writeField(
            @NonNull String fieldName,
            @NonNull T aggregate,
            @NonNull Object newValue) {
        try {
            Field[] fields = aggregate.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (fieldName.equalsIgnoreCase(field.getName())) {
                    field.setAccessible(true);
                    field.set(aggregate, newValue);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Failed to set field " + fieldName + " on object of type " + aggregate.getClass().getName(), e);
        }
    }

    public static Field[] getFields(Object obj) {
        return obj.getClass().getDeclaredFields();
    }

    public static String getFieldName(Field field) {
        return field.getName();
    }

}
