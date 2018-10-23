package domain.partner.domain.request;
import java.util.Date;

public class ChargeResultQueryParam {
	private static final long serialVersionUID = 8616111885168267224L;
	//充值单号
	private String chargeNo;
	//充值时间
	private Date chargeTime;
	//第三方充值批次号
	private String uniqueBatchNo;

	public String getUniqueBatchNo() {
		return uniqueBatchNo;
	}

	public void setUniqueBatchNo(String uniqueBatchNo) {
		this.uniqueBatchNo = uniqueBatchNo;
	}

	public String getChargeNo() {
		return chargeNo;
	}
	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}
	
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	
}
