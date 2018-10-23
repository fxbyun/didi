package domain.partner.domain.request;
import java.io.Serializable;
import java.util.Map;


/**
 * 充值请求
 * 包含请求网关地址和签名后请求网关所需参数
 * 前端拿到后自己构造请求跳转第三方充值网关
 *
 */
public class PartnerChargeRequest implements Serializable{
	

	private static final long serialVersionUID = 7305534897639412943L;
	private Map<String, String> requestParamMap;
	private String gateWayUrl;

	/**
	 * 请求提交地址
	 * @return
	 */
	public String getGateWayUrl() {
		return gateWayUrl;
	}

	public Map<String, String> getRequestParamMap() {
		return requestParamMap;
	}

	/**
	 * 请求提交参数
	 * @param requestParamMap
	 */
	public void setRequestParamMap(Map<String, String> requestParamMap) {
		this.requestParamMap = requestParamMap;
	}

	public void setGateWayUrl(String gateWayUrl) {
		this.gateWayUrl = gateWayUrl;
	}
	
	  
}
