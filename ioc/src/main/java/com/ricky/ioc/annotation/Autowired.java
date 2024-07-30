package com.ricky.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className Autowired
 * @desc
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean required() default true;

}
