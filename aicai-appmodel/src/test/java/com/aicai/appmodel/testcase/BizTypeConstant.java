package com.aicai.appmodel.testcase;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aicai.appmodel.constant.FieldTitle;

public class BizTypeConstant {
	private static Logger log = LoggerFactory.getLogger(BizTypeConstant.class);

	@FieldTitle("非特殊页面")
	public static final long unknown = 0;

	@FieldTitle("图片验证码")
	public static final long checkCodeUrl = 1;
	@FieldTitle("此页面有密码修改链接")
	public static final long passwordBeforeEditUrl = 1 << 1;
	@FieldTitle("此页面有密码修改输入框")
	public static final long passwordEditUrl = 1 << 2;
	@FieldTitle("此链接保存密码")
	public static final long passwordPostToUpdateUrl = 1 << 3;
	public static final long passwordChangeFeatureUrl = passwordBeforeEditUrl | passwordEditUrl | passwordPostToUpdateUrl;

	@FieldTitle("此页面跟下发短信相关")
	public static final long sendSmsFeatureUrl = 1 << 4;
	@FieldTitle("此链接触发下发短信")
	public static final long sendSmsPostUrl = 1 << 5;

	public static final long sendMailFeatureUrl = 1 << 7;
	public static final long SendMailPostUrl = 1 << 6;

	public static final long loginFormUrl = 1 << 8;
	public static final long loginPostUrl = 1 << 9;

	public static final long memberInfoUrl = 1 << 10;

	public static final long moneyInfoUrl = 1 << 11;

	private static final Map<Long, String> bizTypeMap;

	static {
		bizTypeMap = new ConcurrentHashMap<>();
		try {
			for (Field field : BizTypeConstant.class.getFields()) {
				FieldTitle name = field.getAnnotation(FieldTitle.class);
				if (name != null) {
					bizTypeMap.put(field.getLong(null), name.value());
				}
			}
		} catch (Throwable ex) {
			log.error("{msg:'bizTypeMap 初始化失败'}", ex);
		}
	}

	public static Map<Long, String> getBiztypeMap() {
		return bizTypeMap;
	}

}
