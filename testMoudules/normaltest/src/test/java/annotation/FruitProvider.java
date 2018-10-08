package annotation;

import java.lang.annotation.*;

/**
 * auth: shi yi
 * create date: 2018/8/13
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {
    /**
     * 供应商编号
     */
    public int id() default -1;

    /**
     * 供应商名称
     */
    public String name() default "";

    /**
     * 供应商地址
     */
    public String address() default "";

    public String cusTest() default "";
}
