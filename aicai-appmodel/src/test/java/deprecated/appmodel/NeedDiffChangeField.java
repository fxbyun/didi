package deprecated.appmodel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Deprecated 是因为限制太多收益太少
 * 
 * @author zhoufenglokki
 *
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface NeedDiffChangeField {
	byte markNotNullForObjAndForPrimitive() default -47;
}
