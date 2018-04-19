package org.vaalbara.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.*;

/**
 * Created by Huawei on 2018/3/16.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    String description() default "";
}
