package com.aicai.dao.readwrite.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aicai.dao.readwrite.ReadWriteDao;
import com.aicai.dao.readwrite.domain.ReadWriteDaoData;
import com.aicai.dao.readwrite.domain.WebControllerResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 读写分离相关管理 <p>
 * (1)开启读写分离，访问的url类似于：http://localhost:8080/应用上下文/readWrite/openReadWrite.do?daoName=readWriteDao <p>
 *    需要提供的参数说明：<p>
 * 	  参数daoName: 是您要开启读写分离的dao名称（在spring容器中的name）<p>
 * (2)关闭读写分离，访问的url类似于：http://localhost:8080/应用上下文/readWrite/closeReadWrite.do?daoName=readWriteDao <p>
 * 	  需要提供的参数说明：<p>
 *    参数daoName: 是您要关闭读写分离的dao名称（在spring容器中的name）<p>
 * (3)修改权重，访问的url类似于：http://localhost:8080/应用上下文/readWrite/changeWeight.do?daoName=readWriteDao&readRate=5 <p>  
 * 	  需要提供的参数说明：<p>
 * 	  1.参数daoName: 是您要修改权重的dao名称（在spring容器中的name）<p>
 *    2.参数readRate: 读比率(readRate越大，读操作走读库的概率就越大);　约束条件为：1 <= readRate <= 9  <p>
 *    
 * <p>
 * 更多信息请＠see　{@link ReadWriteDao}
 * 
 * @author zhouguangming 
 * 创建时间: 2014年4月23日 上午10:28:19
 */
@Controller
@RequestMapping("/readWrite")
public class ReadWriteSwitchController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 开启读写分离 
	 */
	@RequestMapping("/openReadWrite.do")
	public void openReadWrite(String daoName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.openOrCloseReadWrite(daoName, request, response, true);
	}

	
	/**
	 * 关闭读写分离 
	 */
	@RequestMapping("/closeReadWrite.do")
	public void closeReadWrite(String daoName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.openOrCloseReadWrite(daoName, request, response, false);
	}
	/**
	 * 查看读写分离状态 
	 */
	@RequestMapping("/checkReadWriteStatus.do")
	public void checkReadWriteStatus(String daoName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebControllerResult result = new WebControllerResult();
		if (StringUtils.isBlank(daoName)) {
			result.addErrormsg("daoName can not be empty or readRate should be more than 1 (readRate > 1)");
			response.getWriter().append(toJSON(result));
			return;
		}
		try {
			ReadWriteDaoData data = getDaoData(daoName, request, result);
			result.setDaoData(data);
			response.getWriter().append(toJSON(result));
		} catch (Exception e) {
			result.addErrormsg(e.getMessage());
			log.warn("查看读写状态时发生异常:", e);
			response.getWriter().append(toJSON(result));
		}
	}
	/**
	 * 修改权重
	 */
	@RequestMapping("/changeWeight.do")
	public void changeWeight(String daoName, Integer readRate, HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebControllerResult result = new WebControllerResult();
		if (StringUtils.isBlank(daoName) || readRate == null || readRate < 1) {
			result.addErrormsg("daoName can not be empty or readRate should be more than 1 (readRate > 1)");
		}
		ReadWriteDao readWriteDao = this.getDaoFromSpringContainer(daoName, request, result);
		if (!result.isSuccess()) {
			response.getWriter().append(toJSON(result));
			return;
		}
		try {
			readWriteDao.changeWeight(readRate);
			ReadWriteDaoData data = getDaoData(daoName, request, result);
			result.setDaoData(data);
			response.getWriter().append(toJSON(result));
		} catch (Exception e) {
			result.addErrormsg(e.getMessage());
			log.warn("修改权重时发生异常:", e);
			response.getWriter().append(toJSON(result));
		}
	}
	
    /**
     * 开启或者关闭读写分离
     * 
     * @param enableReadWrite true:开启；　false：关闭
     */
	private void openOrCloseReadWrite(String daoName, HttpServletRequest request, HttpServletResponse response,
			boolean enableReadWrite) throws IOException {
		WebControllerResult result = new WebControllerResult();
		if (StringUtils.isBlank(daoName)) {
			result.addErrormsg("daoName can not be empty");
		}
		ReadWriteDao readWriteDao = this.getDaoFromSpringContainer(daoName, request, result);
		if (!result.isSuccess()) {
			response.getWriter().append(toJSON(result));
			return;
		}
		try {
			readWriteDao.openOrCloseReadWrite(enableReadWrite);
			ReadWriteDaoData data = getDaoData(daoName, request, result);
			result.setDaoData(data);
			response.getWriter().append(toJSON(result));
		} catch (Exception e) {
			result.addErrormsg(e.getMessage());
			log.warn("开启或者关闭读写分离时发生异常:",e);
			response.getWriter().append(toJSON(result));
		}
	}

	/**
	 * 从spring容器中获取读写分离Dao实例
	 */
	private ReadWriteDao getDaoFromSpringContainer(String daoName, HttpServletRequest request, WebControllerResult result) {
		try {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
			ReadWriteDao readWriteDao = context.getBean(daoName, ReadWriteDao.class);
			return readWriteDao;
		} catch (BeansException e) {
			result.addErrormsg("The Dao of " + daoName + " is not exist!}");
			log.warn("从上下文中取dao时发生异常",e);
			return null;
		} catch (Exception e) {
			log.warn("从上下文中取dao时发生异常",e);
			result.addErrormsg(e.getMessage());
			return null;
		}
	}
	
	private ReadWriteDaoData getDaoData(String daoName, HttpServletRequest request, WebControllerResult result) {
		ReadWriteDaoData daoData = new ReadWriteDaoData();
		ReadWriteDao dao = getDaoFromSpringContainer(daoName, request, result);
		if (dao != null) {
			Integer rate = dao.getReadRate();
			boolean open = dao.isEnableReadWrite();
			daoData.setEnableReadWrite(open);
			daoData.setReadRate(rate);
			daoData.setDaoName(daoName);
		}
		return daoData;
	}
	
	private String toJSON(WebControllerResult result) {
		String retStr = JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.UseSingleQuotes,SerializerFeature.PrettyFormat);
		return retStr;
	}
}
