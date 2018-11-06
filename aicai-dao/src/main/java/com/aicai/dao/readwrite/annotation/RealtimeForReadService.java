package com.aicai.dao.readwrite.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.aicai.dao.readwrite.advice.ReadServiceAdvice;

/**
 * 专用于ReadService的实现类中方法上的实时性注解 <p>
 * 注：不要在WriteService的实现类中使用 <p>
 * 说明：<p>
 * 1.当方法上面有标注此注解时，且标注形式为@RealtimeForReadService(isRealtime = true), 表示相关数据库读操作应该走主库（即写库）<p>
 * 2.当方法上面有标注此注解时，且标注形式为@RealtimeForReadService(isRealtime = false), 表示相关数据库读操作应该根据权重的算法来判断，即可能走主库,也可能走从库（即读库）<p>
 * 3.当方法上面没有标注此注解时，行为同第２点说明<p>
 * 
 * @see {@link ReadServiceAdvice}
 * 
 * @author zhouguangming
 * 创建时间: 2014年4月2日 下午7:33:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RealtimeForReadService {
	boolean isRealtime();
}
