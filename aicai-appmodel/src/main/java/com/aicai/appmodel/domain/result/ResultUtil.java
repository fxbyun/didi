package com.aicai.appmodel.domain.result;

import com.aicai.appmodel.constant.UnknownErrorCodeConstant;

public class ResultUtil {
	public static <T extends Result> T convertToOtherResult(T result, Result<?> subResult) {
		result.setErrorMsg(subResult.isSuccess(), subResult.getErrorCode(), subResult.getErrorMsg(), subResult.getInputParamWhereCatch(), subResult.getDetailStack());
		return result;
	}

	public static String convertStackToString(Throwable e) {
		StringBuilder sb = new StringBuilder(512);
		for (StackTraceElement line : e.getStackTrace()) {
			sb.append("\tat ").append(line);
		}
		return sb.toString();
	}

	public static <T extends Result> T buildResultWithErrCanRetry(T result, String inputParamWhereCatch) {
		result.setErrorMsg(false, UnknownErrorCodeConstant.errCanRetry, UnknownErrorCodeConstant.getMsg(UnknownErrorCodeConstant.errCanRetry), inputParamWhereCatch);
		return result;
	}

	public static <T extends Result> T buildResultWithErrCanRetry(T result, String inputParamWhereCatch, Throwable e){
		String detailStack = convertStackToString(e);
		result.setErrorMsg(false, UnknownErrorCodeConstant.errCanRetry, UnknownErrorCodeConstant.getMsg(UnknownErrorCodeConstant.errCanRetry), inputParamWhereCatch, detailStack);
		return result;
	}

	public static String toStringWhereUnsuccess(Result result) {
		StringBuilder sb = new StringBuilder(512);
		// TODO 有陈翔鹏完成
		return sb.toString();
	}
}
