package deprecated.appmodel;

import java.lang.reflect.Field;

import com.aicai.appmodel.constant.UnknownErrorCodeConstant;
import com.aicai.appmodel.exception.InternalRuntimeException;

/**
 * @Deprecated 是因为限制太多收益太少
 * 
 * @author zhoufenglokki
 *
 */
@Deprecated
public interface ShouldDiffChangeWhenSaveByEditor {

	/**
	 * 为什么没有返回值/return，因为希望使用者能花点时间看清源代码，要注意源代码是将 memObjAndReturn 这个对象中的相同字段给抹去了
	 * common-lang3 中的 DiffBuilder 不能同时 setNull 或特殊值表示相同，所以写此代码 貌似采用 java.beans
	 * 中的 PropertyEditor体系 会带来很多复杂性和学习成本，而annotation的出现比PropertyEditor 简便多了
	 * 
	 * @param memObjAndReturn
	 * @param dbObj
	 */
	default public <T> void diffChangeFieldAndMarkNotNull(T dbObj) {
		Field[] fieldList = dbObj.getClass().getDeclaredFields();
		try {
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(NeedDiffChangeField.class)) {
					field.setAccessible(true);
					Object memValue = field.get(this);
					Object dbValue = field.get(dbObj);
					if (memValue == null) {
						continue;
					}
					if (memValue.equals(dbValue)) {
						if (field.getType().isPrimitive()) {
							NeedDiffChangeField annotation = field.getAnnotation(NeedDiffChangeField.class);
							field.set(this, annotation.markNotNullForObjAndForPrimitive());
						} else {
							field.set(this, null);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InternalRuntimeException(e, UnknownErrorCodeConstant.exceptionNeedTechCheck);
		}
	}
}
