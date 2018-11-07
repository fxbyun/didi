package com.aicai.appmodel.constant;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aicai.appmodel.exception.InternalRuntimeException;
import com.alibaba.fastjson.JSON;

/**
 * 
 * 整数枚举性质的字段数据，在常见需求“数据列表显示”、“下拉选择列表”、“判断输入是否在范围内”，有3种解决结构：
 * 
 * (1)枚举。枚举在反序列化和从数据表中搞出来时，是要绝对匹配，如果其他系统产生的值或有的值，本系统没有，
 * 在反序列化或mybatis底层就立刻抛异常终止后续操作，这在新jar发布档期不统一的多war大型分布式系统里很常见，
 * 但数据表里这个数值是对的，只是显示文本没有枚举值配上，并且后续操作大部分不需要该条记录的显示文本。
 * “下拉选择列表”、“判断输入是否在范围内”，这在枚举结构中的解决，每次要采用低性能的反射和从头循环。
 * 
 * (2)常量实例基类 + 反射出常量实例列表 “数据列表显示”：常量类中的文本配给DO，也是需要很多细节代码， 配给VO
 * 也是要从列表中找处理，没像从map中找出来那么容易。每次要采用低性能的反射和从头循环。
 * 
 * (3)常量类 + annotation + mapHolder列表，如 FieldTitleUtil
 * “数据列表显示”：join给显示VO中的文本字段，从map中获得即可。 “下拉选择列表”：直接将map转化成 ajax json，在前端js很容易处理
 * “判断输入是否在范围内”：用map很容易判断。 “EL点号表达式”：如果支持javamap/json的点号文本表达式，也是没问题的
 * 比起(4)的优点是，不限定field的属性和命名为int/id/title，没有层次封装 缺点是，如果“EL点号表达式”不支持map，在jsp tag
 * 中不够方便 并且多了annotation这个东西
 * 
 * (4)常量实例基类 + mapHolder列表，如 ConstantTitleBase 相比(3)
 * 优点：子类可以实现自己的显示列表顺序和group，而没有思想阻抗 缺点：加了一层次封装，且限定field 为int id。
 * 
 * @author zhoufenglokki
 *
 */

public class ConstantTitleBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(ConstantTitleBase.class);

	private int id;
	private String title;
	protected static String titleUnknown = "本机没此数值的说明文本,需要更新jar发布";

	public ConstantTitleBase() {
	}

	public ConstantTitleBase(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title != null ? title : titleUnknown;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleOrDefault(String title) {
		return title != null ? title : titleUnknown;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		ConstantTitleBase other = (ConstantTitleBase) obj;
		return id == other.getId() ? true : false;
	}

	public static <T extends ConstantTitleBase> T valueOf(Class<T> clazz,  Map<Integer, T> constantMap, int id) {
		T enumInstance = constantMap.get(id);
		if (enumInstance != null) {
			return enumInstance;
		}

		try {
			enumInstance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			String msg = "{id:" + id + "}";
			log.error(msg, e);
			throw new InternalRuntimeException(msg, e);
		}

		enumInstance.setId(id);
		enumInstance.setTitle(titleUnknown);
		return enumInstance;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}
