package com.aicai.appmodel.domain.result;

@Deprecated
/**
 * Deprecated：先转用BaseResult/ModelResult/BatchWriteResult
 * @author zhoufenglokki
 *
 * @param <T>
 */
public interface Result<T> {

	/**
	 * true，表示操作成功，写成功或读出的操作是正确的。
	 * 实现类默认值一般应设置为true
	 * false则表示各种错误，model里的数据不可投入利用
	 */
	public boolean isSuccess();

	public void setSuccess(boolean success);
	public T getModel();
	public void setModel(T model);
	public boolean isReadFromCache();
	public void setReadFromCache(boolean readFromCache);
	
	/**
	 * 人眼可理解的错误消息，或模糊或精细，一般未知错误是模糊的
	 */
	public String getErrorMsg();
	
	/**
	 * 常量来自ErrorCodeConstant.java
	 */
	public int getErrorCode();
	
	/**
	 * 用字符串表示的异常堆栈
	 */
	public String getDetailStack();
	
	/**
	 * 发生未知异常时的输入参数，或可恢复的消息。用严格的json格式表示。
	 * json格式内容的组织可用String.format()
	 */
	public String getInputParamWhereCatch();

	/**
	 * 没有异常引发的操作终止
	 * @param success
	 * @param errorCode
	 * @param errorMsg
	 * @param inputParamWhereCatch
	 */
	public void setErrorMsg(boolean success, int errorCode, String errorMsg, String inputParamWhereCatch);

	/**
	 * 由异常引发的操作终止
	 * @param success
	 * @param errorCode
	 * @param errorMsg
	 * @param inputParamWhereCatch
	 * @param detailStack
	 */
	public void setErrorMsg(boolean success, int errorCode, String errorMsg, String inputParamWhereCatch, String detailStack);	
}
