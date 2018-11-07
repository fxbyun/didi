package com.aicai.appmodel.constant;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在常量类的 static{} 里调用 setupConstantMapByFieldTitle()，并将返回的constantMap hold住。
 * 整数枚举性质的字段数据，在常见需求“数据列表显示”、“下拉选择列表”、“判断输入是否在范围内”，有3种解决结构：
 * 
 * (1)枚举。枚举在反序列化和从数据表中搞出来时，是要绝对匹配，如果其他系统产生的值或有的值，本系统没有，
 * 在反序列化或mybatis底层就立刻抛异常终止后续操作，这在新jar发布档期不统一的多war大型分布式系统里很常见，
 * 但数据表里这个数值是对的，只是显示文本没有枚举值配上，并且后续操作大部分不需要该条记录的显示文本。
 * “下拉选择列表”、“判断输入是否在范围内”，这在枚举结构中的解决，每次要采用低性能的反射和从头循环。
 * 
 * (2)常量实例基类 + 反射出常量实例列表
 * “数据列表显示”：常量类中的文本配给DO，也是需要很多细节代码，
 * 配给VO 也是要从列表中找处理，没像从map中找出来那么容易。每次要采用低性能的反射和从头循环。
 * 
 * (3)常量类 + annotation + mapHolder列表，如 FieldTitleUtil
 * “数据列表显示”：join给显示VO中的文本字段，从map中获得即可。
 * “下拉选择列表”：直接将map转化成 ajax json，在前端js很容易处理
 * “判断输入是否在范围内”：用map很容易判断。
 * “EL点号表达式”：如果支持javamap/json的点号文本表达式，也是没问题的
 * 比起(4)的优点是，不限定field的属性和命名为int/id/title，没有层次封装
 * 缺点是，如果“EL点号表达式”不支持map，在jsp tag 中不够方便
 * 并且多了annotation这个东西
 * 
 * (4)常量实例基类 + mapHolder列表，如 ConstantTitleBase
 * 相比(3)
 * 优点：子类可以实现自己的显示列表顺序和group，而没有思想阻抗
 * 缺点：加了一层次封装，且限定field 为int id。
 * 
 * @author zhoufenglokki
 *
 */
public class FieldTitleUtil {
	private static Logger log = LoggerFactory.getLogger(FieldTitleUtil.class);

	public static Map<Long, String> setupConstantMapByFieldTitle(Class constantClass) {
		Map<Long, String> constantMap = new ConcurrentSkipListMap<>();

		try {
			for (Field field : constantClass.getFields()) {
				FieldTitle anotation = field.getAnnotation(FieldTitle.class);
				if (anotation != null) {
					constantMap.put(field.getLong(null), anotation.value());
				}
			}
		} catch (Throwable ex) {
			log.error("{msg:'constantMap 初始化失败'}", ex);
		}
		return constantMap;
	}

	public String getTitleById(String title){
		return title != null ? title : "本机没此数值的说明文本,需要更新jar发布";
	}

	public String getTitleById(long id, Map<Long, String> constantMap){
		String title = constantMap.get(id);
		return title != null ? title : "本机没此数值的说明文本,需要更新jar发布";
	}
	
}
