package domain.partner.domain.request;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateChargeRequestParam {
	
	private static final long serialVersionUID = -6979318569869106143L;
	//充值单号
	private String chargeNo;
	//用户在自己系统的ID
	private String memberId;
	//金额
	private BigDecimal amount;
	// 商品名称
	private String chargeName;
	// 商品描述
	private String chargeDetail;
	//充值单下单时间
	private Date chargeTime;
	//充值所需银行卡号
	private String bankCardNo;
	//真实姓名
	private String trueName;
	//身份证号
	private String certNo;
	//发起充值IP
	private String clientIp;
	//充值成功后所返回的地址
	private String returnUrl;
	//充值渠道所需业务参数
	private Map<String,Object> extraParam = new HashMap<String,Object>();
	public String getChargeNo() {
		return chargeNo;
	}
	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public String getChargeDetail() {
		return chargeDetail;
	}
	public void setChargeDetail(String chargeDetail) {
		this.chargeDetail = chargeDetail;
	}
	public Map<String, Object> getExtraParam() {
		return extraParam;
	}
	public void setExtraParam(Map<String, Object> extraParam) {
		this.extraParam = extraParam;
	}
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	
}
