package annotation;

import java.lang.annotation.*;

/**
 * auth: shi yi
 * create date: 2018/8/13
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}