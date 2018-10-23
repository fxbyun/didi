package domain.partner.domain.response;
import java.io.Serializable;
import java.math.BigDecimal;

public class PartnerChargeResult implements Serializable {
	
	private static final long serialVersionUID = -3230513650942603335L;
	/**
	 * 商户充值充值号
	 */
	private String chargeNo;
	/**
	 * 用户在合作方的充值号
	 */
	private String partnerChargeNo;
	
	/**
	 * 充值成功金额
	 */
	private BigDecimal amount;
	
	/**
	 * 充值结果
	 */
	private String result;
	
	public String getChargeNo() {
		return chargeNo;
	}
	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}
	public String getPartnerChargeNo() {
		return partnerChargeNo;
	}
	public void setPartnerChargeNo(String partnerChargeNo) {
		this.partnerChargeNo = partnerChargeNo;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
