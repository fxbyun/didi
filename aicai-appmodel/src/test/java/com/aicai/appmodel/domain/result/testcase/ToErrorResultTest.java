package com.aicai.appmodel.domain.result.testcase;

import org.junit.Assert;
import org.junit.Test;

import com.aicai.appmodel.constant.UnknownErrorCodeConstant;
import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.support.NeedResult;
import com.aicai.appmodel.domain.result.support.UnsuccessResult;

public class ToErrorResultTest {

	@Test
	public void test() {
		NeedResult result = returnNeedResult();
		if (result.isSuccess()) {
			Assert.assertFalse("success", result.isSuccess());
		}
	}

	private NeedResult returnNeedResult() {
		return returnUnsuccessResult().toErrorResult(new NeedResult());
	}

	private BaseResult returnUnsuccessResult() {
		BaseResult result = new UnsuccessResult();
		return result.withError(UnknownErrorCodeConstant.exceptionCanRetry);
	}
}
