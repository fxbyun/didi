package com.aicai.dao.example.domain.query;

import com.aicai.dao.domain.NeedAttachBit;

public class QueryGroupPlanOption implements NeedAttachBit {
	private long needAttachBit;
	private boolean needUpdateAttach = false;

	@Override
	public void setNeedAttach(long neetAttachBit) {
		this.needAttachBit |= neetAttachBit;
	}

	@Override
	public boolean isNeedAttach(long neetAttachBit) {
		return (this.needAttachBit & neetAttachBit) > 0;
	}

	public boolean isNeedUpdateAttach() {
		return needUpdateAttach;
	}

	public void setNeedUpdateAttach(boolean needUpdateAttach) {
		this.needUpdateAttach = needUpdateAttach;
	}

}
