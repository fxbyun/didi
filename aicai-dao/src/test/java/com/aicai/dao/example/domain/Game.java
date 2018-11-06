package com.aicai.dao.example.domain;

import com.aicai.dao.domain.DescriptionId;

public enum Game implements DescriptionId{
	FC_SSQ(101, "双色球", 7, null),
	FC_DF6j1(105, "东方6+1", 7, null);
	
	private int index;

	private String description;
	
	@Deprecated
    /**
     * 开奖号码个数
     * deprecated原因：每个彩种每个由外部决定的环节，都有特定的一大串校验规则，放在这里只能辅助实现一个小点的校验，对整体庞大质量校验没有意义
     */
    private int resultNum;
    
    /** 父彩种 */
    private Game parent;
	
	private Game(int index, String description, int resultNum, Game parent){
		this.index = index;
		this.description = description;
		this.resultNum = resultNum;
		this.parent = parent;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public int getResultNum() {
		return resultNum;
	}

	public Game getParent() {
		return parent;
	}
}
