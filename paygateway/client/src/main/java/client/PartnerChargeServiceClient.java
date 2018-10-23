package client;

import domain.partner.PartnerChargeService;
import domain.partner.domain.request.ChargeResultQueryParam;
import domain.partner.domain.request.CreateChargeRequestParam;
import domain.partner.domain.request.PartnerChargeRequest;
import domain.partner.domain.response.PartnerChargeResult;

/**
 * 客户端直接调用这个
 */
public class PartnerChargeServiceClient implements PartnerChargeService {

	PartnerChargeService partnerChargeService;


	public void createChargeRequest(CreateChargeRequestParam param, int chargePartnerIndex) {
		partnerChargeService.createChargeRequest(param,chargePartnerIndex);
	}

	public void queryPartnerChargeResult(ChargeResultQueryParam param, int chargePartnerIndex) {
		partnerChargeService.queryPartnerChargeResult(param,chargePartnerIndex);
	}
}
