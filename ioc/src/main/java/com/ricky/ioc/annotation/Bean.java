package com.ricky.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/30
 * @className Bean
 * @desc
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
}
