package com.aicai.profiler.connector.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 跳过打印
 * 作用于 类/方法/参数 上，对已经委托给自动代理/精细化打印的方法有效，可以跳过该日志打印
 * 
 * @project aicai-profiler
 * @author van
 * @date 2012-11-1
 * Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights reserved.
 */
@Target({ ElementType.PARAMETER,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipDigest {

}
