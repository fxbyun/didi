package domain.partner;

import domain.PartnerChargeWay;
import domain.partner.domain.request.ChargeResultQueryParam;
import domain.partner.domain.request.CreateChargeRequestParam;
import domain.partner.domain.request.PartnerChargeRequest;
import domain.partner.domain.response.PartnerChargeResult;

/***
 * 第三方合作商充值服务
 *
 */
public interface PartnerChargeService {
	
	
	/**
	 * 创建合作商充值请求,客户端拿着参数直接请求合作商网关
	 * @param param
	 * @param chargePartnerIndex {@link PartnerChargeWay}
	 * @return
	 */
	public  void createChargeRequest(CreateChargeRequestParam param, int chargePartnerIndex);

	/**
	 * 查询充值结果
	 * @param param
	 * @param chargePartnerIndex {@link PartnerChargeWay}
	 * @return
	 */
	public  void queryPartnerChargeResult(ChargeResultQueryParam param, int chargePartnerIndex);
}
