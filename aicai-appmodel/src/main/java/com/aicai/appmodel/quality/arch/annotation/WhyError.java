package com.aicai.appmodel.quality.arch.annotation;

public enum WhyError {
	/**
	 * 事务代码不正确，混和了事务，一些操作不需要混和在真正所需要的事务中
	 */
	transactionMix_level1,

	/**
	 * 对某组件的用法不符合其设计
	 */
	errorToComponentDesign_level2;
}
