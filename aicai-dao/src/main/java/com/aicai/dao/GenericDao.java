package com.aicai.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;

import com.aicai.appmodel.page.DataPage;

public interface GenericDao {

	public long generateSequence(String sqlNameWithNameSpace);

	public <T> int insertAndReturnAffectedCount(String sqlNameWithNameSpace, T obj);

	public <T> int insertAndSetupId(String sqlNameWithNameSpace, T obj);

	/**
	 * 小批量的insert values 可以采用这种方式， 但超过200条记录的，应采用 mybatis batch
	 * 方式，并需要建一个独立的mybatisTemplateBatch bean
	 */
	public <T> int insertWithMultiValues(String sqlNameWithNameSpace, List<T> objs, int batchSize);

	public int update(String sqlNameWithNameSpace, Map<String, Object> param);

	public int updateByObj(String sqlNameWithNameSpace, Object param);

	public <T> T queryUnique(String statement, long id);

	public <T> T queryUnique(String statement, String idStr);

	public <T> T queryUnique(String sqlNameWithNameSpace, Map<String, Object> map);

	@Deprecated
	/**
	 * 转用queryUnique()或带exceptionExceedCount的
	 * 
	 * @param statement
	 * @param id
	 * @return
	 */
	public <T> T queryOne(String statement, long id);

	@Deprecated
	/**
	 * 转用queryUnique()或带exceptionExceedCount的
	 * 
	 * @param statement
	 * @param idStr
	 * @return
	 */
	public <T> T queryOne(String statement, String idStr);

	/**
	 * 采用mybatis.selectList获取第一个，判断超过exceptionExceedCount个就抛异常防止内存占用超出预期，fail-fast
	 * 
	 * @param statement
	 * @param idStr
	 * @param exceptionExceedCount
	 * @return
	 */
	public <T> T queryOne(String statement, String idStr, int exceptionExceedCount);

	@Deprecated
	/**
	 * 转用queryUnique()或带exceptionExceedCount的
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @return
	 */
	public <T> T queryOne(String sqlNameWithNameSpace, Map<String, Object> map);

	/**
	 * 采用mybatis.selectList获取第一个，判断超过exceptionExceedCount个就抛异常防止内存占用超出预期，fail-fast
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @param exceptionExceedCount
	 * @return
	 */
	public <T> T queryOne(String sqlNameWithNameSpace, Map<String, Object> map, int exceptionExceedCount);

	public Object queryObject(String sqlNameWithNameSpace, Map<String, Object> map);

	@Deprecated
	/**
	 * deprecated因为不推荐悲观锁方式
	 * 
	 * @param statement
	 * @param id
	 * @return
	 */
	public <T> T queryOneWithPessimisticLock(String statement, long id);

	public <T> T queryOneByObject(String sqlNameWithNameSpace, String mapKey, Object mapValue);

	public int queryCount(String sqlNameWithNameSpace, Map<String, Object> map);

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
	public int queryInt(String sqlNameWithNameSpace, Map<String, Object> map);

	public <T> List<T> queryList(String sqlNameWithNameSpace, Map<String, Object> map, DataPage<T> page);

	public <T> DataPage<T> queryPage(String countSqlNameWithNameSpace, String rsSqlNameWithNameSpace,
			Map<String, Object> map, DataPage<T> page);

	public <T> List<T> queryList(String sqlNameWithNameSpace, Map<String, Object> map);

	/**
	 * 采用mybatis.selectList获取第一个，判断超过exceptionExceedCount个就抛异常防止内存占用超出预期，fail-fast
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @param exceptionExceedCount
	 * @return
	 */
	public <T> List<T> queryList(String sqlNameWithNameSpace, Map<String, Object> map, int exceptionExceedCount);

	@Deprecated
	/**
	 * Deprecated，是因为经过逐步改造后，queryList()也实现了返回值泛型灵活性 此返回值定义在使用上比queryList()更灵活
	 * 
	 * @param sqlNameWithNameSpace
	 * @param map
	 * @return
	 */
	public <T> List<T> queryListByMap(String sqlNameWithNameSpace, Map<String, Object> map);

	public <T> List<T> queryList(String sqlNameWithNameSpace);

	public <T> List<T> queryIdIn(String sqlNameWithNameSpace, long[] idList);

	public <T> List<T> queryIdIn(String sqlNameWithNameSpace, String[] idList);

	public <K, V> Map<K, V> selectOneToMap(String sqlNameWithNameSpace, Map<K, V> param);

	public List<BatchResult> flushStatements();
}
