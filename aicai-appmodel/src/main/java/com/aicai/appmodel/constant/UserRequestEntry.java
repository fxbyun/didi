package com.aicai.appmodel.constant;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 本类谨为ConstantTitleBase用法 的 example
 * @author zhoufenglokki
 *
 */
public class UserRequestEntry extends ConstantTitleBase {
	private static final long serialVersionUID = 1L;

	protected static ConcurrentSkipListMap<Integer, UserRequestEntry> constantMap = new ConcurrentSkipListMap<>();

	public UserRequestEntry() {	// 无参数的public 构造器，必不可少，有些时候反序列化不了，或无法调用无参数的 class.newInstance()
	}

	public UserRequestEntry(int id, String title) {
		super(id, title);
		constantMap.put(id, this); // 此行代码最关键
	}

	public static UserRequestEntry notSetup = new UserRequestEntry(0, "某环节疏忽未设置");
	public static UserRequestEntry loginId = new UserRequestEntry(1, "登录名");
	public static UserRequestEntry phoneNum = new UserRequestEntry(2, "手机号");
	public static UserRequestEntry weixin = new UserRequestEntry(4, "微信");
	public static UserRequestEntry email = new UserRequestEntry(3, "邮箱");
	
	public static ConcurrentSkipListMap<Integer, ? extends UserRequestEntry> getConstantMap() {
		return constantMap;
	}
}
