package alainvanhout.rest.annotations;

import alainvanhout.rest.request.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface RestInstanceRelative {
    String value() default "";

    HttpMethod[] methods() default {HttpMethod.GET};
}
