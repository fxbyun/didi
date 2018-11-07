package com.aicai.appmodel.dateservice;

/**
 * 原封不动地返回调用者计算好的时间，不管原来类型是什么。
 * 通过这个，可以让单元测试“介入”跟now时间相关的mock
 * @author zhoufeng
 *
 */
public class DateHelperImpl implements DateHelper {

	@Deprecated
	@Override
	/**
	 * 已Deprecated，
	 * 一次调用过程可能几处用到DateHelper，而不同处的DateHelper可能需要准备的时间mock不一样，
	 * 为了让测试人员更方便地准备数据，请用两个参数的calcDate()，keyForIdentity可以取得比较符合当前处的时间意义，
	 * 比如nowTime
	 */
	public <T> T calcDate(T obj) {
		return obj;
	}

	@Override
	/**
	 * 一次调用过程可能几处用到DateHelper，而不同处的DateHelper可能需要准备的时间mock不一样，
	 * 为了让测试人员更方便地准备数据，keyForIdentity可以取得比较符合当前处的时间意义，比如nowTime
	 */
	public <T> T calcDate(T obj, String keyForIdentity) {
		return obj;
	}

}
