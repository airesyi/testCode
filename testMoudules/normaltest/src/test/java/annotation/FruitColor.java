package annotation;

import java.lang.annotation.*;

/**
 * auth: shi yi
 * create date: 2018/8/13
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     */
    public enum Color {
        BLUE, RED, GREEN
    }

    /**
     * 颜色属性
     */
    Color fruitColor() default Color.GREEN;

}
