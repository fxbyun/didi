package com.aicai.appmodel.dateservice;

/**
 * 
 * @author zhoufenglokki
 *
 */
public class DateHelperHolder {

	/**
	 * 通过staticHolder 减轻了spring注入代码  
	 */
	private static DateHelper dateHelper = new DateHelperImpl();

	/**
	 * 通过便捷方法，减轻了一个点号的使用
	 * 
	 * @param obj
	 * @param keyForIdentity
	 * @return
	 */
	public static <T> T calcDate(T obj, String keyForIdentity) {
		return dateHelper.calcDate(obj, keyForIdentity);
	}

	public static void setDateHelper(DateHelper dateHelperMock) {
		dateHelper = dateHelperMock;
	}
}
