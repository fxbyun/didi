package domain.partner.helipay;
import domain.PayGateway;

/**
 * 网关协议:合利宝支付（只是示例用，所有方法都返回void）
 *
 */
public interface HelipayService {

	public PayGateway getGateWay();

	/**
	 *  创建合利宝代付
	 */
	void createPayrollTradefer();
}
