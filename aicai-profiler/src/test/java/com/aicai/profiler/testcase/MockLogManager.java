package com.aicai.profiler.testcase;

import com.aicai.profiler.core.ExecNode;
import com.aicai.profiler.core.LogManager;

public class MockLogManager implements LogManager {

	private ExecNode root;
	
	public ExecNode getRoot() {
		return root;
	}

	public void setRoot(ExecNode root) {
		this.root = root;
	}

	@Override
	public String showTree(ExecNode root) {
		this.root = root;
		return null;
	}

}
