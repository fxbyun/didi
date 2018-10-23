package domain.partner.env.helipay;

/**
 * 合利宝账户
 */
public class HelipayThirdAccount {

    //合利宝 私钥
    private String signKey_transfer;
    //合利宝 代付URL
    private  String urlTransfer;
    //商户号
    private String customerNumber;
    //MD5 快捷支付
    private String quickPayMd5key;
    //快捷支付url
    private String quickPayUrl;
    //MD5 公共产品
    private String publicMd5key;
    //公共产品URL
    private String publicUrl;
    //充值成功返回通知地址
    private String quickPayNotifyUrl;

    public String getQuickPayNotifyUrl() {
        return quickPayNotifyUrl;
    }

    public void setQuickPayNotifyUrl(String quickPayNotifyUrl) {
        this.quickPayNotifyUrl = quickPayNotifyUrl;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public String getPublicMd5key() {
        return publicMd5key;
    }

    public void setPublicMd5key(String publicMd5key) {
        this.publicMd5key = publicMd5key;
    }

    public String getQuickPayMd5key() {
        return quickPayMd5key;
    }

    public void setQuickPayMd5key(String quickPayMd5key) {
        this.quickPayMd5key = quickPayMd5key;
    }

    public String getQuickPayUrl() {
        return quickPayUrl;
    }

    public void setQuickPayUrl(String quickPayUrl) {
        this.quickPayUrl = quickPayUrl;
    }



    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSignKey_transfer() {
        return signKey_transfer;
    }

    public void setSignKey_transfer(String signKey_transfer) {
        this.signKey_transfer = signKey_transfer;
    }


    public String getUrlTransfer() {
        return urlTransfer;
    }

    public void setUrlTransfer(String urlTransfer) {
        this.urlTransfer = urlTransfer;
    }
}
