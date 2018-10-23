package paygateway.partner.helipay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.PartnerChargeWay;
import domain.partner.domain.request.ChargeResultQueryParam;
import domain.partner.domain.request.CreateChargeRequestParam;
import domain.partner.domain.request.PartnerChargeRequest;
import domain.partner.domain.response.PartnerChargeResult;
import domain.partner.env.helipay.HelipayThirdAccount;
import paygateway.partner.helipay.PartnerChargeProtocal;
import paygateway.partner.helipay.service.AbstractHelipayService;

public class HelipayChargeProxyImpl extends AbstractHelipayService implements PartnerChargeProtocal {

	private HelipayThirdAccount helipayAccount;

	@Override
	public int getChargeWay() {
		return PartnerChargeWay.HELI_PAY_WAP_CHARGE;
	}

	@Override
	public PartnerChargeRequest createChargeRequest(CreateChargeRequestParam chargeParam) {
		return null;
	}

	@Override
	public PartnerChargeResult queryPartnerChargeResult(ChargeResultQueryParam param) {
		return null;
	}



}
