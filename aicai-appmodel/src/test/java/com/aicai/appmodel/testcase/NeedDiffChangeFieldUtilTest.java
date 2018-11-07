package com.aicai.appmodel.testcase;

import org.junit.Test;

import com.aicai.appmodel.mock.Story;

import deprecated.appmodel.ShouldDiffChangeWhenSaveByEditor;

public class NeedDiffChangeFieldUtilTest {

	@Test
	public void test() {
		Story memStory = new Story();
		memStory.setTitle("test1");
		memStory.setImageFileId("file1");
		memStory.setReason("reason1");
	
		Story dbStory = new Story();
		dbStory.setTitle("test1");
		dbStory.setImageFileId("file2");
		dbStory.setReason("reason2");
		
		memStory.diffChangeFieldAndMarkNotNull(dbStory);
		System.err.println(memStory);
	}
}
