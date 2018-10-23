package paygateway.partner.helipay;

import domain.partner.domain.request.ChargeResultQueryParam;
import domain.partner.domain.request.CreateChargeRequestParam;
import domain.partner.domain.request.PartnerChargeRequest;
import domain.partner.domain.response.PartnerChargeResult;

/**
 * 各种支付方式的充值请求
 *
 */
public interface PartnerChargeProtocal{
	
	
	public int getChargeWay();
	
	/***
	 *<pre>
	 *为充值构造请求参数
	 *不同的充值渠道商有不同的协议实现
	 *</pre>
	 * @param
	 * @return
	 */
	public PartnerChargeRequest createChargeRequest(CreateChargeRequestParam chargeParam);


	public PartnerChargeResult queryPartnerChargeResult(ChargeResultQueryParam param);
}
