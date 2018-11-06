package com.aicai.dao.domain;

/**
 * NeedAttachBit 和 QueryOptionBit 有点不一样，
 * NeedAttachBit 和 HasLoadedAttach 比较一致，可以用同样的constant位值，
 * 所以为了NeedAttachBit的纯粹，定义了隔离开了的QueryOptionBit
 * 
 * @author zhoufeng
 *
 */
public interface QueryOptionBit {
	public void setOptionBit(long optionBit);
	public boolean isSetOptionBit(long optionBit);
}
