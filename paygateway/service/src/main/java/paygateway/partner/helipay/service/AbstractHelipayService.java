package paygateway.partner.helipay.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import domain.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;

/**
 * 请求第三方的工具service
 */
public abstract class AbstractHelipayService {
    protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 请求服务
     * @param requestUrl
     * @param params
     * @return
     */
    protected String httpPost(String gateWay, Map<String, String> paramMap){
        paramMap = filterNotEmptyMap(paramMap);
        logger.info("传递参数:[{}],域名:[{}]", JSON.toJSONString(paramMap),gateWay);
        String retString = HttpUtil.httpSSLClientPostWithJson(gateWay, JSON.toJSONString(paramMap), "utf-8");
        logger.info("接口返回参数:[{}],域名:[{}]", retString,gateWay);
        return retString;
    }	/**
     * 请求服务
     * @param requestUrl
     * @param params
     * @return
     */
    protected String httpClientPost(String gateWay, Map<String, String> paramMap){
        paramMap = filterNotEmptyMap(paramMap);
        logger.info("传递参数:[{}],域名:[{}]", JSON.toJSONString(paramMap),gateWay);
        String retString = HttpUtil.httpClientPost(gateWay, paramMap, "utf-8");
        logger.info("接口返回参数:[{}],域名:[{}]", retString,gateWay);
        return retString;
    }

    private Map<String, String> filterNotEmptyMap(Map<String, String> paramMap){
        Map<String, String> ret = Maps.newHashMap();
        for(String key:paramMap.keySet()){
            ret.put(key, paramMap.get(key));
        }
        return ret;
    }
}
