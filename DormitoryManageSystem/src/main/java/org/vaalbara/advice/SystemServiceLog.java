package org.vaalbara.advice;

import java.lang.annotation.*;

/**
 * Created by Huawei on 2018/3/17.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
    String description() default "";
}
