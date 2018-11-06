package com.aicai.dao.example.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RamSequenceManager implements SequenceManager {

	private Map<String, AtomicLong> map = new HashMap<String, AtomicLong>();
	
	@Override
	public long getSequence(String dataType) {
		AtomicLong sequence = map.get(dataType);
		if(sequence == null){
			sequence = initSequence(dataType);
		}
		return sequence.incrementAndGet();
	}
	
	private synchronized AtomicLong initSequence(String dataType){
		AtomicLong sequence = map.get(dataType);
		if(sequence == null){
			sequence = new AtomicLong(0);
			map.put(dataType, sequence);
		}
		return sequence;
	}
}
