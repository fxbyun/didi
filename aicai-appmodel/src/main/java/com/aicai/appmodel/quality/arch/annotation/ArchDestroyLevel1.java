package com.aicai.appmodel.quality.arch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.TYPE})
public @interface ArchDestroyLevel1 {
	WhyError[] why();
	String reviewer();
	String[] comment();
	String commentTime();
}
