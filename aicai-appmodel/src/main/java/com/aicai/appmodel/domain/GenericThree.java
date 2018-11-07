package com.aicai.appmodel.domain;

public class GenericThree<One, Two, Three> {
	private One one;
	private Two two;
	private Three tree;

	public GenericThree() {
	}

	public GenericThree(One one, Two two, Three tree) {
		this.one = one;
		this.two = two;
		this.tree = tree;
	}

	public One getOne() {
		return one;
	}

	public void setOne(One one) {
		this.one = one;
	}

	public Two getTwo() {
		return two;
	}

	public void setTwo(Two two) {
		this.two = two;
	}

	public Three getTree() {
		return tree;
	}

	public void setTree(Three tree) {
		this.tree = tree;
	}
}
