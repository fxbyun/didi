package com.aicai.appmodel.domain;

/**
 * 
 * 这里是否要实现序列化接口，等到有人提需求时再定，目前只用作单war内的返回值上下文传递
 * 
 * @author zhoufeng
 *
 * @param <One>
 * @param <Two>
 */
public class GenericTwo<One, Two> {
	private One one;
	private Two two;

	/**
	 * 用于两个数据“先后不同时间”设置
	 */
	public GenericTwo() {
	}

	public GenericTwo(One one, Two two){
		this.one = one;
		this.two = two;
	}

	public One getOne() {
		return one;
	}

	public void setOne(One one) {
		this.one = one;
	}

	public Two getTwo() {
		return two;
	}

	public void setTwo(Two two) {
		this.two = two;
	}
}
