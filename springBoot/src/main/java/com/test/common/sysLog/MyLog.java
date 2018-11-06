package com.test.common.sysLog;

import java.lang.annotation.*;

/**
 * auth: shi yi
 * create date: 2018/11/6
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface MyLog {
    String value() default "";
}
