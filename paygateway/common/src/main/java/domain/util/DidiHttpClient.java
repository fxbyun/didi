package domain.util;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;

public class DidiHttpClient extends DefaultHttpClient {
	
	public DidiHttpClient(){
		super();
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");  
		    X509TrustManager tm = new X509TrustManager(){
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
						String authType) throws CertificateException {
				}
	
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
						String authType) throws CertificateException {
				}
	
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}  
		    };  
		    ctx.init(null, new  TrustManager[]{tm}, null);  
		    SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
		    ClientConnectionManager ccm = this.getConnectionManager();  
		    SchemeRegistry sr = ccm.getSchemeRegistry();  
		    sr.register(new Scheme("https", 443, ssf));  
		} catch (Exception e) {
			throw new RuntimeException("创建忽略https证书的httpClient发生异常:",e);
		}
	}
	
	
	@Override
	protected HttpParams createHttpParams() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setTcpNoDelay(params, true);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpProtocolParams.setUserAgent(params, "Mozilla/4.7 (compatible; MSIE 8.0; Windows NT 6.1; Maxthon;)");
		return params;
	}
	
	
}
