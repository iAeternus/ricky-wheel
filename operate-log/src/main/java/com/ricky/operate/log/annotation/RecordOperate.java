package com.ricky.operate.log.annotation;

import com.ricky.operate.log.converter.Converter;

import java.lang.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className RecordOperate
 * @desc
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordOperate {

    String desc() default "";

    Class<? extends Converter<?>> converter();

}
