package com.aicai.appmodel.domain.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 根据某id/no去数据表里查询，如果查不到时(没有故障)，success为true而model为null
 * 
 * @author zhoufeng
 * 
 * @param <T>
 */
public class ModelResult<T> extends BaseResult {
	private static final long serialVersionUID = -1731185697573008846L;

	/**
	 * 根据某id/no去数据表里查询，如果查不到时(没有故障)，success为true而model为null
	 */
	private T model;
	private boolean readFromCache = true; // 默认值不能随便改

	public ModelResult() {
	}

	public ModelResult(T model) {
		this.model = model;
	}

	/**
	 * 根据某id/no去数据表里查询，如果查不到时(没有故障)，success为true而model为null
	 */
	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <SubClass extends ModelResult> SubClass withModel(T model) {
		this.model = model;
		return (SubClass) this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <SubClass extends ModelResult> SubClass withModelFromCache(T model) {
		this.model = model;
		return (SubClass) this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <SubClass extends ModelResult> SubClass withModelFromDb(T model) {
		this.model = model;
		this.readFromCache = false;
		return (SubClass) this;
	}

	public boolean isReadFromCache() {
		return readFromCache;
	}

	public void setReadFromCache(boolean readFromCache) {
		this.readFromCache = readFromCache;
	}

	/**
	 * 确实需要将系统信息过滤掉的:
	 * (1)在子类里 override 本方法，将model 里的系统字段先过滤掉
	 * (2)接着 super.filterSuccessJsonToClient(jsonObj)
	 * 而不是此无参方法，提供此无参方法，是为了方便子类 override 方法 copy 前2行代码
	 * 
	 * BaseDO 子类，想要挑些字段转换成json，或者转换成JSONObject 后 remove 掉一些字段的，
	 * 可以定义自己的，可以多个的 convertToObjectMap()，然后setModel<Map<String,Object>>()
	 * 再让json框架一步转换成json。
	 * 很难做出一个共用体制做好这个，而且也不想将特定json框架的使用，绑定在方法参数或返回值上(Map<String, Object>还可以接受)
	 * 
	 * @return
	 */
	public String filterSuccessJsonToClient() {
		JSONObject jsonObj = (JSONObject) JSON.toJSON(this);
		/*
		 * 子类方法应在此处过滤掉model里的系统字段
		 * */
		super.filterSuccessJsonToClient(jsonObj);

		return jsonObj.toJSONString();
	}
}
