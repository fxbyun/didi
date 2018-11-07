package com.aicai.appmodel;

import org.junit.Test;

import com.aicai.appmodel.quality.arch.annotation.ArchDestroyLevel1;
import com.aicai.appmodel.quality.arch.annotation.WhyError;

public class AppTest {

	@Test
	public void test() {
		System.out.println(String.format("{msg:'abnormalCause can`t be null', prevOrderId:%1$d}", 1234));
	}

	@SuppressWarnings("unused")
	private void exampleForArchDestroyLevel(){
		@ArchDestroyLevel1(why = WhyError.transactionMix_level1, reviewer="周锋", comment="comment", commentTime="2012-12-24")
		int transactionMix;
		
		@ArchDestroyLevel1(why = WhyError.transactionMix_level1, reviewer="周锋", comment={"comment1", "comment2"}, commentTime="2012-12-24")
		int transactionMix2;
		

		Runnable a = new Runnable(){

			@Override
			@ArchDestroyLevel1(why = WhyError.transactionMix_level1,
					comment={"本意是想把payStatus的update和钱包流水作事务，这里将几个sql操作混在一起了",
					"orderStatus 可先update",
					"payStatus和insert钱包流水按目前事务意愿可作事务，但这个事务比较假，因为order表和plan表是不同的数据库，是要分布式事务才正确，或做好redo",
					"payStatus和钱包流水事务需要用redo分离",
					"需要payStatus增加'扣款中'，并增加可以让财务人员redo修复或定时补偿的接口"},
					reviewer="周锋",
					commentTime="2012-12-24")
			public void run() {
				// TODO Auto-generated method stub
				
			}};
	}
}
