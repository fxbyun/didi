package com.aicai.appmodel.quality.code.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.LOCAL_VARIABLE})
public @interface QualityDestroyLevel1 {
	Why[] why();
	String reviewer();
	String[] comment();
	String commentTime();
}
