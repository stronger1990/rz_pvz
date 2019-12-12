package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author dean.lee
 * https://github.com/dean4lee/springboot-demo/tree/master/limiter
 * https://blog.csdn.net/weixin_45052750/article/details/90545470
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiter {

    /**
     * 从第一次访问接口的时间到cycle周期时间内，无法超过frequency次，默认2次
     * @return
     */
    int frequency() default 2;

    /**
     * 周期时间,默认10秒内
     * @return
     */
    int cycle() default 10 * 1000;

    /**
     * 返回的错误信息
     * @return
     */
    String message() default "请求过于频繁，请稍后继续操作";
}
