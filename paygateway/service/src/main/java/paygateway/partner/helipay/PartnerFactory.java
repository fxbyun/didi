package paygateway.partner.helipay;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

/**
 * 工厂类
 */
public class PartnerFactory {

    private Map<Integer, PartnerChargeProtocal> chargeProxys = Maps.newHashMap();

    public Map<Integer, PartnerChargeProtocal> getChargeProxys() {
        return chargeProxys;
    }

    public void setChargeProxys(List<PartnerChargeProtocal> chargeProxyList) {
        if (chargeProxyList.size() > 0) {
            for (PartnerChargeProtocal proxy : chargeProxyList) {
                chargeProxys.put(proxy.getChargeWay(), proxy);
            }
        }
    }
}
