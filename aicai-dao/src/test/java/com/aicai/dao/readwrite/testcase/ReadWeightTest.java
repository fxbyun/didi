package com.aicai.dao.readwrite.testcase;

import org.junit.Test;

public class ReadWeightTest {
	private static final int TOTAL = Integer.MAX_VALUE / 100;
	volatile int position = 1;
	int[] weightPool = { 0b0_000_000_001, 0b0_000_100_001, 0b0_001_001_001,	0b0_101_001_001, 
			0b1_010_101_010, 0b1_010_101_011, 0b1_010_101_111, 0b1_011_101_111, 0b1_011_111_111};
	private int weight = weightPool[0];
	
	@Test
	public void test() {
		setWeight(2);
		long readCount = 0;
		for (int i = 0; i < TOTAL; i++) {
			if (isFromRead()) {
				readCount++;
			}
		}
		System.out.println(readCount);
	}

	private boolean isFromRead() {
		if (position > 0b1_000_000_000) {
			position = 1;
		}
		
		boolean readWeight = (weight & position) == position;
		position = position << 1;
		return  readWeight;
	}

	public void setWeight(int readRate) {
		if(readRate > 9){
			readRate = 9;
		}
		if(readRate < 1){
			readRate = 1;
		}
		
		weight = weightPool[readRate - 1];
	}
}
