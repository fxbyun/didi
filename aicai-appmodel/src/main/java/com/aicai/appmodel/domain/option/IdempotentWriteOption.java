package com.aicai.appmodel.domain.option;

/**
 * 先占位，引发符雄/周科荣思考要填什么getter/setter
 * 
 * 其实最重要的幂等：MemberWalletLog里用 transType + logId做幂等不够清晰/不太好。
 * 
 * 
 * @author zhoufeng
 *
 */
public interface IdempotentWriteOption {
	void setIdempotentIdSource(String idFromThisTable);
	void setIdempotentId(String id);
	void setIdempotentIdSource(Integer idFromThisTable);
	void setIdempotentId(Long id);
}
