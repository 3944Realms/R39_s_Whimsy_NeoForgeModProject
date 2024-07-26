package com.r3944realms.whimsy.utils.ModAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.SOURCE)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
public @interface NeedCompletedInFuture {
    String futureTarget() default "";
}
