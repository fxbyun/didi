package domain.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpUtil {

	private static transient final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static void main(String[] args) {
		// System.out.println(getRemoteStr("http://www.2caipiao.com", "GBK"));
		String url = "http://info.sporttery.cn/basketball/match_list.php";
		String returnXML = httpGet(url, "GBK");
		System.out.println(returnXML);
	}

	public static  String createUrlParam(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (StringUtils.isBlank(value)) {
				continue;
			}
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 * 使用httpclient进行http请求
	 *
	 * @param url
	 * @param encode
	 * @author kerong.zhou
	 * @return
	 */
	public static String httpGet(String url, String encode) {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
			HttpConnectionParams.setSoTimeout(params, 30000);
			HttpUriRequest httpReq = new HttpGet(url);
			ResponseHandler<String> responseHandler = new MyResponseHandler(encode);
			String returnStr = httpclient.execute(httpReq, responseHandler);
			return returnStr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用HTTP进行POST请求
	 *
	 * @param url
	 *            需要请求的URL
	 * @param paramMap
	 *            参数键值对
	 * @param encode
	 *            请求编码
	 * @author kerong.zhou
	 * @return
	 */
	public static String httpClientPost(String url, Map<String, String> paramMap, final String encode) {
		HttpClient httpclient = new DidiHttpClient();
		HttpPost httppost = new HttpPost(url);
		HttpParams params = httpclient.getParams();
		// 设置超时5秒
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 30000);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(paramMap.keySet().size());
			for (String key : paramMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key) == null ? "" : paramMap.get(key)));
			}
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encode));
			HttpResponse response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			return entity == null ? null : EntityUtils.toString(entity, encode);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}finally {
			try{
				httppost.releaseConnection();
				HttpClientUtils.closeQuietly(httpclient);
				httpclient = null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	public static CloseableHttpClient getCloseableNOSSLVerificationHttpClient(){
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
		    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
		    Registry<ConnectionSocketFactory> soRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
		    		.register("http", PlainConnectionSocketFactory.INSTANCE)
		    		.register("https", socketFactory).build();

		    RequestConfig requestConfig =RequestConfig.custom()
		    		.setConnectTimeout(5000)
		    		.setSocketTimeout(30000)
		    		.build();

		    PoolingHttpClientConnectionManager connectionManager= new PoolingHttpClientConnectionManager(soRegistry);
		    CloseableHttpClient client= HttpClients.custom().setConnectionManager(connectionManager)
		    		.setDefaultRequestConfig(requestConfig)
		    		.build();
		    return client;
		}catch(Exception e){
			throw new RuntimeException("创建忽略https证书的httpClient发生异常:",e);
		}
	}


	public static String httpSSLClientPostWithJson(String url, String json, final String encode) {
		CloseableHttpClient httpclient = getCloseableNOSSLVerificationHttpClient();
		CloseableHttpResponse response = null;
		HttpPost httppost = new HttpPost(url);
		try {
	        httppost.addHeader(HTTP.CONTENT_TYPE, "application/json");
	        StringEntity se = new StringEntity(json, encode);
	        se.setContentType("text/json");
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httppost.setEntity(se);
			response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			String ret = entity == null ? null : EntityUtils.toString(entity, encode);
			return ret;
		}catch (Throwable e) {
			throw new RuntimeException(e);
		}finally {
			try{
				httppost.releaseConnection();
				HttpClientUtils.closeQuietly(response);
				HttpClientUtils.closeQuietly(httpclient);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static String httpSSLGet(String url, String encode) {
		CloseableHttpClient httpclient = getCloseableNOSSLVerificationHttpClient();
		CloseableHttpResponse response = null;
		HttpGet httppost = new HttpGet(url);
		try {
			response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			String ret = entity == null ? null : EntityUtils.toString(entity, encode);
			return ret;
		}catch (Throwable e) {
			throw new RuntimeException(e);
		}finally {
			try{
				httppost.releaseConnection();
				HttpClientUtils.closeQuietly(response);
				HttpClientUtils.closeQuietly(httpclient);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	public static String httpSSLClientPost(String url, Map<String, String> paramMap, final String encode) {
		CloseableHttpClient httpclient = getCloseableNOSSLVerificationHttpClient();
		CloseableHttpResponse response = null;
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(paramMap.keySet().size());
			for (String key : paramMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key) == null ? "" : paramMap.get(key)));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encode));
			}
			response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			String ret = entity == null ? null : EntityUtils.toString(entity, encode);
			return ret;
		}catch (Throwable e) {
			throw new RuntimeException(e);
		}finally {
			try{
				httppost.releaseConnection();
				HttpClientUtils.closeQuietly(response);
				HttpClientUtils.closeQuietly(httpclient);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static String httpSSLClientPostAccept400RespCode(String url, Map<String, String> paramMap, final String encode) {
		CloseableHttpClient httpclient = getCloseableNOSSLVerificationHttpClient();
		CloseableHttpResponse response = null;
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(paramMap.keySet().size());
			for (String key : paramMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key) == null ? "" : paramMap.get(key)));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encode));
			}
			response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300 && statusLine.getStatusCode() != 400) {
				logger.error("http请求响应码异常【{}】",statusLine.getStatusCode()+"-"+statusLine.getReasonPhrase());
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			String ret = entity == null ? null : EntityUtils.toString(entity, encode);
			return ret;
		}catch (Throwable e) {
			throw new RuntimeException(e);
		}finally {
			try{
				httppost.releaseConnection();
				HttpClientUtils.closeQuietly(response);
				HttpClientUtils.closeQuietly(httpclient);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用HTTP进行POST请求
	 *
	 * @param url
	 *            需要请求的URL
	 * @param paramMap
	 *            参数键值对
	 * @param encode
	 *            请求编码
	 * @author kerong.zhou
	 * @return
	 */
	public static String httpClientPostThrowNativeRunTimeException(String url, Map<String, String> paramMap, final String encode) {
		HttpClient httpclient = new DidiHttpClient();
		HttpPost httppost = new HttpPost(url);
		HttpParams params = httpclient.getParams();
		// 设置超时5秒
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 30000);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(paramMap.keySet().size());
			for (String key : paramMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key) == null ? "" : paramMap.get(key)));
			}
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encode));
			HttpResponse response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			return entity == null ? null : EntityUtils.toString(entity, encode);
		} catch (SocketTimeoutException e) {// 请求超时异常直接抛出
			throw new RuntimeException(e);
		} catch (ConnectException e) {
			throw new RuntimeException(e);
		} catch (SocketException e) {// IO异常捕获
			throw new RuntimeException(e);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用HTTP进行POST请求:使用默认编码
	 *
	 * @param url
	 *            需要请求的URL
	 * @param paramMap
	 *            参数键值对
	 * @author kerong.zhou
	 * @return
	 */
	public static String httpClientPost(String url, Map<String, String> paramMap) {
		return httpClientPost(url, paramMap, SystemConstants.GOBAL_ENCODE);
	}

	/**
	 * 使用HTTPCLIENT POST XML 数据
	 *
	 * @param url
	 *            请求的地址
	 * @param xml
	 *            xml文件
	 * @param encode
	 *            发送的文件的编码
	 * @author kerong.zhou
	 * @return
	 */
	public static String postXml(String url, String xml, String encode) {
		HttpEntity entity = null;
		CloseableHttpClient httpclient = getCloseableNOSSLVerificationHttpClient();
		CloseableHttpResponse response = null;
		HttpPost httppost = new HttpPost(url);
		try {

			StringEntity myEntity = new StringEntity(xml, encode);
			httppost.addHeader("Content-Type", "text/xml");
			httppost.setEntity(myEntity);
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			return entity == null ? null : EntityUtils.toString(entity, encode);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}finally {
			try{
				EntityUtils.consumeQuietly(entity);
				httppost.releaseConnection();
				HttpClientUtils.closeQuietly(response);
				HttpClientUtils.closeQuietly(httpclient);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用HTTPCLIENT POST XML 数据
	 *
	 * @param url
	 *            请求的地址
	 * @contentType 类型
	 * @param encode
	 *            发送的文件的编码
	 * @author fx
	 * @return
	 */
	public static String postString(String url, String contentStr, String contentType, String encode) {
		HttpEntity entity = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
			HttpConnectionParams.setSoTimeout(httpParams, 60000);

			HttpPost httppost = new HttpPost(url);
			StringEntity myEntity = new StringEntity(contentStr, encode);
			httppost.addHeader("Content-Type", contentType);
			httppost.setEntity(myEntity);
			HttpResponse response = httpclient.execute(httppost);
			entity = response.getEntity();
			return entity == null ? null : EntityUtils.toString(entity, encode);
		} catch (Throwable e) {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e1) {
					throw new RuntimeException(e1);
				}
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用系统默认编码进行POST XML文件
	 *
	 * @param url
	 * @param xml
	 * @author kerong.zhou
	 * @return
	 */
	public static String postXml(String url, String xml) {
		return postXml(url, xml, SystemConstants.GOBAL_ENCODE);
	}
}
