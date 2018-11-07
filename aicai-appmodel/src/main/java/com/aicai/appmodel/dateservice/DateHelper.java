package com.aicai.appmodel.dateservice;

/**
 * 根据输入时间返回所需的时间，让外部"介入"时间的计算。
 * 比如让测试DateHelperMock返回他所准备测试数据的时间范围
 * http://www.infoq.com/cn/news/2009/12/Testing-time-dates
 * http://www.bigvisible.com/2009/11/unit-testing-and-dates/
 * 
 * @author zhoufeng
 *
 */
public interface DateHelper {

	/**
	 * Deprecated，建议使用calcDate(T, String)
	 * 
	 * DateHelperImpl.java 会原封不动返回输入的
	 * DatehelperMock会根据keyForIdentity返回addDateMock()的
	 * 
	 * @param obj
	 * @return
	 */
	@Deprecated
	public <T> T calcDate(T obj);

	/**
	 * 对于一次执行流程有多次调用DateHelper的地方，可以更轻易地辨识不同的使用点，
	 * 也更容易使用addDateMock()设置对应的值。
	 * 而keyForIdentity，对于线上代码没有影响。
	 * 
	 * DateHelperImpl.java 会原封不动返回输入的
	 * DatehelperMock会根据keyForIdentity返回addDateMock()的
	 * 
	 * @param object
	 * @param keyForIdentity
	 * @return
	 */
	public <T> T calcDate(T obj, String keyForIdentity);
}
