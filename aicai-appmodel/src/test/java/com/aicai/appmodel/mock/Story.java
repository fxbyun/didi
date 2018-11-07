package com.aicai.appmodel.mock;

import com.aicai.appmodel.domain.BaseDO;
import com.alibaba.fastjson.JSON;

import deprecated.appmodel.NeedDiffChangeField;
import deprecated.appmodel.ShouldDiffChangeWhenSaveByEditor;

public class Story extends BaseDO implements ShouldDiffChangeWhenSaveByEditor {

	private static final long serialVersionUID = -5860307559515721481L;

	@NeedDiffChangeField
	private String title;
	
	@NeedDiffChangeField
	private String imageFileId;
	
	@NeedDiffChangeField
	private String summary;
	private int status;
	
	@NeedDiffChangeField
	private String reason;
	
	@NeedDiffChangeField
	private String storyFileId;
	private String itemIds;
	private long sortTimestamp;
	private long attachBit;

	public long getSortTimestamp() {
		return sortTimestamp;
	}

	public void setSortTimestamp(long sortTimestamp) {
		this.sortTimestamp = sortTimestamp;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStoryFileId() {
		return storyFileId;
	}

	public void setStoryFileId(String storyFileId) {
		this.storyFileId = storyFileId;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

}
