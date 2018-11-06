package com.aicai.dao;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aicai.appmodel.exception.InternalRuntimeException;
import com.aicai.appmodel.page.DataPage;

public class GenericMybatisDao implements GenericDao {
	private static final Logger log = LoggerFactory.getLogger(GenericMybatisDao.class);

	private SqlSession mybatisTemplate;

	@Deprecated
	/**
	 * deprecated是因为MyBatis的大小写不符合规范，也不好看。
	 * 
	 * @param myBatisTemplate
	 */
	public void setMyBatisTemplate(SqlSession mybatisTemplate) {
		this.mybatisTemplate = mybatisTemplate;
	}

	public void setMybatisTemplate(SqlSession mybatisTemplate) {
		this.mybatisTemplate = mybatisTemplate;
	}

	@Override
	public long generateSequence(String sqlNameWithNameSpace) {
		return mybatisTemplate.<Long> selectOne(sqlNameWithNameSpace);
	}

	/**
	 * 插入数据，返回affectedCount，没有改变id
	 * 
	 * @param sqlNameWithNameSpace
	 * @param obj
	 * @return
	 */
	@Override
	public <T> int insertAndReturnAffectedCount(String sqlNameWithNameSpace, T obj) {
		return mybatisTemplate.insert(sqlNameWithNameSpace, obj);
	}

	/**
	 * 插入数据，sql里将id设置进obj，返回affectedCount
	 * 
	 * @param sqlNameWithNameSpace
	 * @param obj
	 * @return 因为返回值是int型，不是long型，所以不是id，而是affectedCount
	 */
	@Override
	public <T> int insertAndSetupId(String sqlNameWithNameSpace, T obj) {
		return mybatisTemplate.insert(sqlNameWithNameSpace, obj);
	}

	/**
	 * 小批量的insert values 可以采用这种方式， 但超过200条记录的，应采用 mybatis batch
	 * 方式，并需要建一个独立的mybatisTemplateBatch bean
	 */
	@Override
	public <T> int insertWithMultiValues(String sqlNameWithNameSpace, List<T> objs, int batchSize) {
		int totalCnt = 0;
		List<List<T>> batchList = splitList(objs, batchSize);
		for (List<T> subList : batchList) {
			totalCnt += mybatisTemplate.insert(sqlNameWithNameSpace, subList);
		}
		return totalCnt;
	}

	@Override
	public int update(String sqlNameWithNameSpace, Map<String, Object> param) {
		return mybatisTemplate.update(sqlNameWithNameSpace, param);
	}

	@Override
	public int updateByObj(String sqlNameWithNameSpace, Object param) {
		return mybatisTemplate.update(sqlNameWithNameSpace, param);
	}

	@Override
	public <T> T queryOne(String statement, long id) {
		return mybatisTemplate.selectOne(statement, id);
	}

	@Override
	public <T> T queryOne(String statement, String idStr) {
		return mybatisTemplate.selectOne(statement, idStr);
	}

	@Override
	public <T> T queryOne(String sqlNameWithNameSpace, Map<String, Object> map) {
		return mybatisTemplate.<T> selectOne(sqlNameWithNameSpace, map);
	}

	@Override
	public Object queryObject(String sqlNameWithNameSpace, Map<String, Object> map) {
		return mybatisTemplate.selectOne(sqlNameWithNameSpace, map);
	}

	@Override
	public <T> T queryOneWithPessimisticLock(String statement, long id) {
		return mybatisTemplate.selectOne(statement, id);
	}

	@Override
	public <T> T queryOne(String sqlNameWithNameSpace, String idStr, int exceptionExceedCount) {
		List<T> list = mybatisTemplate.<T> selectList(sqlNameWithNameSpace, idStr);
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > exceptionExceedCount) {
			String msg = "{sqlId:'" + sqlNameWithNameSpace
					+ "', msg:'采用mybatis.selectList获取第一个，判断超过exceptionExceedCount个就抛异常防止内存占用超出预期，fail-fast', exceptionExceedCount:"
					+ exceptionExceedCount + "}";
			log.error(msg);
			throw new InternalRuntimeException(msg);
		} else {
			return list.get(0);
		}
	}

	@Override
	public <T> T queryOne(String sqlNameWithNameSpace, Map<String, Object> map, int exceptionExceedCount) {
		List<T> list = mybatisTemplate.<T> selectList(sqlNameWithNameSpace, map);
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > exceptionExceedCount) {
			String msg = "{sqlId:'" + sqlNameWithNameSpace
					+ "', msg:'采用mybatis.selectList获取第一个，判断超过exceptionExceedCount个就抛异常防止内存占用超出预期，fail-fast', exceptionExceedCount:"
					+ exceptionExceedCount + "}";
			log.error(msg);
			throw new InternalRuntimeException(msg);
		} else {
			return list.get(0);
		}
	}

	@Override
	public <T> T queryOneByObject(String sqlNameWithNameSpace, String mapKey, Object mapValue) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(mapKey, mapValue);
		return mybatisTemplate.selectOne(sqlNameWithNameSpace, paramMap);
	}

	@Override
	public <T> T queryUnique(String statement, long id) {
		return mybatisTemplate.selectOne(statement, id);
	}

	@Override
	public <T> T queryUnique(String statement, String idStr) {
		return mybatisTemplate.selectOne(statement, idStr);
	}

	@Override
	public <T> T queryUnique(String sqlNameWithNameSpace, Map<String, Object> map) {
		return mybatisTemplate.<T> selectOne(sqlNameWithNameSpace, map);
	}

	/**
	 * select count(*) where xxx
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @return
	 */
	@Override
	public int queryCount(String sqlNameWithNameSpace, Map<String, Object> map) {
		return mybatisTemplate.<Integer> selectOne(sqlNameWithNameSpace, map);
	}

	/**
	 * 因为 sun java编译器(javac)有个bug，会导致 "int result =
	 * 泛型返回值的queryOne()"有编译错误，而eclipse编译器是正确的。
	 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
	 * 所以增加这个没有泛型的queryInt()
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @return
	 */
	public int queryInt(String sqlNameWithNameSpace, Map<String, Object> map) {
		return mybatisTemplate.<Integer> selectOne(sqlNameWithNameSpace, map);
	}

	/**
	 * select * where 分页
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @param page
	 * @return
	 */
	@Override
	public <T> List<T> queryList(String sqlNameWithNameSpace, Map<String, Object> map, DataPage<T> page) {
		map.put("startIndex", page.getFirst());
		map.put("endIndex", page.getEndIndex());
		map.put("pageSize", page.getPageSize());
		return queryList(sqlNameWithNameSpace, map);
	}

	/**
	 * select * where
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @return
	 */
	@Override
	public <T> List<T> queryList(String sqlNameWithNameSpace, Map<String, Object> map) {
		return mybatisTemplate.selectList(sqlNameWithNameSpace, map);
	}

	@Override
	public <T> List<T> queryListByMap(String sqlNameWithNameSpace, Map<String, Object> map) {
		return mybatisTemplate.selectList(sqlNameWithNameSpace, map);
	}

	@Override
	public <T> List<T> queryList(String sqlNameWithNameSpace, Map<String, Object> map, int exceptionExceedCount) {
		List<T> list = this.queryList(sqlNameWithNameSpace, map);
		if (list.size() > exceptionExceedCount) {
			String msg = "{sqlId:'" + sqlNameWithNameSpace
					+ "', msg:'list个数超出范围,抛异常防止内存占用超出预期，fail-fast', exceptionExceedCount:" + exceptionExceedCount + "}";
			log.error(msg);
			throw new InternalRuntimeException(msg);
		}
		return list;
	}

	/**
	 * 慎用。不能用没有where条件的sql
	 * 
	 * @param sqlNameWithNameSpace
	 * @return
	 */
	@Override
	public <T> List<T> queryList(String sqlNameWithNameSpace) {
		return mybatisTemplate.selectList(sqlNameWithNameSpace);
	}

	@Override
	public <T> List<T> queryIdIn(String sqlNameWithNameSpace, long[] idList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", idList);
		return mybatisTemplate.selectList(sqlNameWithNameSpace, paramMap);
	}

	/**
	 * 需要预防idList拼接后sql超长超出数据库sql长度的情况
	 * 
	 * @param sqlNameWithNameSpace
	 * @param idList
	 * @return
	 */
	@Override
	public <T> List<T> queryIdIn(String sqlNameWithNameSpace, String[] idList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", idList);
		return mybatisTemplate.selectList(sqlNameWithNameSpace, paramMap);
	}

	@Override
	public <K, V> Map<K, V> selectOneToMap(String sqlNameWithNameSpace, Map<K, V> param) {
		return mybatisTemplate.selectOne(sqlNameWithNameSpace, param);
	}

	@Override
	public <T> DataPage<T> queryPage(String countSqlNameWithNameSpace, String rsSqlNameWithNameSpace,
			Map<String, Object> paramMap, DataPage<T> page) {
		if (page.isNeedTotalCount()) {
			int count = this.queryCount(countSqlNameWithNameSpace, paramMap);
			page.setTotalCount(count);
		}

		if (page.isNeedData()) {
			if (page.isNeedTotalCount()) {
				if (page.getTotalCount() > 0) {
					List<T> resultList = this.queryList(rsSqlNameWithNameSpace, paramMap, page);
					page.setDataList(resultList);
				} else {
					// 先前代码已知totalCount为0了，并且DataPage中dataList默认值为null
					page.setDataList(new ArrayList<T>());
				}
			}

			if (!page.isNeedTotalCount()) {
				List<T> resultList = this.queryList(rsSqlNameWithNameSpace, paramMap, page);
				page.setDataList(resultList);
			}
		}
		return page;
	}

	public List<BatchResult> flushStatements() {
		return mybatisTemplate.flushStatements();
	}

	private static <T> List<List<T>> splitList(List<T> list, int pageSize) {
		int total = list.size();
		int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		List<List<T>> result = new ArrayList<List<T>>();
		for (int i = 0; i < pageCount; i++) {
			int start = i * pageSize;
			int end = start + pageSize > total ? total : start + pageSize;
			List<T> subList = list.subList(start, end);
			result.add(subList);
		}
		return result;
	}

}
