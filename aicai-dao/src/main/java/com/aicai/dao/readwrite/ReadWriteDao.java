package com.aicai.dao.readwrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import com.aicai.appmodel.exception.InternalRuntimeException;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.aicai.dao.readwrite.controller.ReadWriteSwitchController;
import com.aicai.dao.util.ReadServiceThreadContextHolder;

/**
 * 读写分离Dao
 * <p>
 * 说明：
 * <p>
 * 1.读写分离关闭时，所有操作（读写操作）都走主库
 * <p>
 * 2.读写分离开启时，分以下情况：
 * <p>
 * (1).所有的写操作都走主库
 * <p>
 * (2).来自于WriteService的所有读操作都走主库
 * <p>
 * (3).来自于ReadService的读操作根据相应条件决定走主库还是走读库，具体情况如下：
 * <p>
 * 1).该读操作处于事务中，走主库
 * <p>
 * 2).{@link ReadServiceThreadContextHolder#isRealtime()} 方法返回true，走主库
 * <p>
 * 3).{@link ReadWriteSupport#isFromRead} 方法返回false，走主库
 * <p>
 * 4).{@link ReadWriteSupport#isFromRead} 方法返回true，走读库
 * <p>
 * 详细信息请 @see {@link ReadWriteSupport#readFromMaster()}
 * <p>
 * <p>
 * 另：关于读写分离的开启关闭、权重调整等相关信息，请 @see {@link ReadWriteSwitchController}
 * <p>
 * <p>
 * 更多信息请查看: http://wiki.inzwc.com/wiki/index.php/AicaiDao
 * 
 * @author zhouguangming 创建时间: 2014年4月10日 上午9:14:34
 */
public class ReadWriteDao implements GenericDao {
	/**
	 * 日志记录
	 */
	private static final Logger log = LoggerFactory.getLogger(ReadWriteDao.class);
	/**
	 * 写与实时读SqlSessionTemplate; 注：走主(写)库
	 */
	private SqlSession writeTemplate;
	/**
	 * 只读SqlSessionTemplate; 注：走从(读)库
	 */
	private SqlSession readTemplate;
	private boolean enableReadWrite = false;
	/**
	 * 读比率
	 * <p>
	 * 约束条件为：1 <= readRate <= {@link ReadWriteSupport#WEIGHT_RATE_POOL.length};
	 * <p>
	 * 注：(1)当{@link ReadWriteSupport#WEIGHT_RATE_POOL.length}=9时， readRate =
	 * 1表示走读库与写库的比例是1:9; readRate = ５表示走读库与写库的比例是1:1;
	 * <p>
	 * (2)走读库的概率与readRate值成正比
	 */
	private int readRate = 1;
	private final ReadWriteSupport readWriteSupport = new ReadWriteSupport();

	@Override
	public long generateSequence(String sqlNameWithNameSpace) {
		return writeTemplate.<Long> selectOne(sqlNameWithNameSpace);
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
		return writeTemplate.insert(sqlNameWithNameSpace, obj);
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
		return writeTemplate.insert(sqlNameWithNameSpace, obj);
	}

	@Override
	public <T> int insertWithMultiValues(String sqlNameWithNameSpace, List<T> objs, int batchSize) {
		int totalCnt = 0;
		List<List<T>> batchList = splitList(objs, batchSize);
		for (List<T> subList : batchList) {
			totalCnt = totalCnt + writeTemplate.insert(sqlNameWithNameSpace, subList);
		}
		return totalCnt;
	}

	@Override
	public int update(String sqlNameWithNameSpace, Map<String, Object> param) {
		return writeTemplate.update(sqlNameWithNameSpace, param);
	}

	@Override
	public int updateByObj(String sqlNameWithNameSpace, Object param) {
		return writeTemplate.update(sqlNameWithNameSpace, param);
	}

	@Override
	public <T> T queryOne(String statement, long id) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(statement, id);
		}

		T t = readTemplate.selectOne(statement, id);
		if (t == null) {
			return writeTemplate.selectOne(statement, id);
		} else {
			return t;
		}
	}

	@Override
	public <T> T queryOne(String statement, String idStr) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(statement, idStr);
		}

		T t = readTemplate.selectOne(statement, idStr);
		if (t == null) {
			return writeTemplate.selectOne(statement, idStr);
		} else {
			return t;
		}
	}

	@Override
	public <T> T queryOne(String sqlNameWithNameSpace, Map<String, Object> map) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.<T> selectOne(sqlNameWithNameSpace, map);
		}

		T t = readTemplate.<T> selectOne(sqlNameWithNameSpace, map);
		if (t == null) {
			return writeTemplate.<T> selectOne(sqlNameWithNameSpace, map);
		} else {
			return t;
		}
	}

	@Override
	public Object queryObject(String sqlNameWithNameSpace, Map<String, Object> map) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(sqlNameWithNameSpace, map);
		}

		Object object = readTemplate.selectOne(sqlNameWithNameSpace, map);
		if (object == null) {
			return writeTemplate.selectOne(sqlNameWithNameSpace, map);
		} else {
			return object;
		}
	}

	@Override
	public <T> T queryOneWithPessimisticLock(String statement, long id) {
		return writeTemplate.selectOne(statement, id);
	}

	@Override
	public <T> T queryOne(String sqlNameWithNameSpace, String idStr, int exceptionExceedCount) {
		List<T> list;
		if (readWriteSupport.readFromMaster()) {
			list = writeTemplate.<T> selectList(sqlNameWithNameSpace, idStr);
		} else {
			list = readTemplate.<T> selectList(sqlNameWithNameSpace, idStr);
			if (list == null || list.isEmpty()) {
				list = writeTemplate.<T> selectList(sqlNameWithNameSpace, idStr);
			}
		}

		if (list == null || list.isEmpty()) {
			return null;
		}

		if (list.size() > exceptionExceedCount) {
			String msg = "{sqlId:'" + sqlNameWithNameSpace + "', msg:'采用mybatis.selectList获取第一个，判断超过exceptionExceedCount个就抛异常防止内存占用超出预期，fail-fast', exceptionExceedCount:" + exceptionExceedCount + "}";
			log.error(msg);
			throw new InternalRuntimeException(msg);
		} else {
			return list.get(0);
		}
	}

	@Override
	public <T> T queryOne(String sqlNameWithNameSpace, Map<String, Object> map, int exceptionExceedCount) {
		List<T> list;
		if (readWriteSupport.readFromMaster()) {
			list = writeTemplate.<T> selectList(sqlNameWithNameSpace, map);
		} else {
			list = readTemplate.<T> selectList(sqlNameWithNameSpace, map);
			if (list == null || list.isEmpty()) {
				list = writeTemplate.<T> selectList(sqlNameWithNameSpace, map);
			}
		}

		if (list == null || list.isEmpty()) {
			return null;
		}

		if (list.size() > exceptionExceedCount) {
			String msg = "{sqlId:'" + sqlNameWithNameSpace + "', msg:'采用mybatis.selectList获取第一个，判断超过exceptionExceedCount个就抛异常防止内存占用超出预期，fail-fast', exceptionExceedCount:" + exceptionExceedCount + "}";
			log.error(msg);
			throw new InternalRuntimeException(msg);
		} else {
			return list.get(0);
		}
	}

	@Override
	public <T> T queryOneByObject(String sqlNameWithNameSpace, String mapKey, Object mapValue) {
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put(mapKey, mapValue);
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(sqlNameWithNameSpace, paramMap);
		}

		T t = readTemplate.selectOne(sqlNameWithNameSpace, paramMap);
		if (t == null) {
			return writeTemplate.selectOne(sqlNameWithNameSpace, paramMap);
		} else {
			return t;
		}
	}

	@Override
	public <T> T queryUnique(String statement, long id) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(statement, id);
		}

		T t = readTemplate.selectOne(statement, id);
		if (t == null) {
			return writeTemplate.selectOne(statement, id);
		} else {
			return t;
		}
	}

	@Override
	public <T> T queryUnique(String statement, String idStr) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(statement, idStr);
		}

		T t = readTemplate.selectOne(statement, idStr);
		if (t == null) {
			return writeTemplate.selectOne(statement, idStr);
		} else {
			return t;
		}
	}

	@Override
	public <T> T queryUnique(String sqlNameWithNameSpace, Map<String, Object> map) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.<T> selectOne(sqlNameWithNameSpace, map);
		}

		T t = readTemplate.<T> selectOne(sqlNameWithNameSpace, map);
		if (t == null) {
			return writeTemplate.<T> selectOne(sqlNameWithNameSpace, map);
		} else {
			return t;
		}
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
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.<Integer> selectOne(sqlNameWithNameSpace, map);
		}
		return readTemplate.<Integer> selectOne(sqlNameWithNameSpace, map);
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
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(sqlNameWithNameSpace, map);
		}
		return readTemplate.<Integer> selectOne(sqlNameWithNameSpace, map);
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
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectList(sqlNameWithNameSpace, map);
		}
		return readTemplate.selectList(sqlNameWithNameSpace, map);
	}

	@Override
	public <T> List<T> queryListByMap(String sqlNameWithNameSpace, Map<String, Object> map) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectList(sqlNameWithNameSpace, map);
		}
		return readTemplate.selectList(sqlNameWithNameSpace, map);
	}

	@Override
	public <T> List<T> queryList(String sqlNameWithNameSpace, Map<String, Object> map, int exceptionExceedCount) {
		List<T> list = this.queryList(sqlNameWithNameSpace, map);
		if (list.size() > exceptionExceedCount) {
			String msg = "{sqlId:'" + sqlNameWithNameSpace + "', msg:'list个数超出范围,抛异常防止内存占用超出预期，fail-fast', exceptionExceedCount:" + exceptionExceedCount + "}";
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
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectList(sqlNameWithNameSpace);
		}
		return readTemplate.selectList(sqlNameWithNameSpace);
	}

	@Override
	public <T> List<T> queryIdIn(String sqlNameWithNameSpace, long[] idList) {
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("idList", idList);
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectList(sqlNameWithNameSpace, paramMap);
		}
		return readTemplate.selectList(sqlNameWithNameSpace, paramMap);
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
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("idList", idList);
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectList(sqlNameWithNameSpace, paramMap);
		}
		return readTemplate.selectList(sqlNameWithNameSpace, paramMap);
	}

	@Override
	public <K, V> Map<K, V> selectOneToMap(String sqlNameWithNameSpace, Map<K, V> param) {
		if (readWriteSupport.readFromMaster()) {
			return writeTemplate.selectOne(sqlNameWithNameSpace, param);
		}
		return readTemplate.selectOne(sqlNameWithNameSpace, param);
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
					page.setDataList(new ArrayList<T>(0));
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
		return writeTemplate.flushStatements();
	}

	/**
	 * 初始化方法
	 * <p>
	 * 注：由spring容器在实例化此类时调用, 其它类不应该调用此方法
	 */
	public void init() {
		if (!enableReadWrite) {
			return;
		}

		readWriteSupport.setWeight(readRate);
	}

	/**
	 * 根据读比率值更改读写库的权重
	 * <p>
	 * 注：
	 * <p>
	 * 考虑到性能方面，{@link ReadWriteSupport#weightRate}
	 * 没有声明为volatile，所以在方法中循环10次,以减小发生可见性问题的概率
	 * 
	 * 
	 * @param readRate
	 *            读比率
	 */
	public void changeWeight(int readRate) {
		for (int i = 0; i < 10; i++) {
			readWriteSupport.setWeight(readRate);
		}
		this.readRate = readRate;
	}

	/**
	 * 开启或者关闭读写分离
	 * <p>
	 * 注：
	 * <p>
	 * 考虑到性能方面，{@link #enableReadWrite} 没有声明为volatile，所以在方法中循环10次,以减小发生可见性问题的概率
	 * 
	 * @param enableReadWrite
	 *            true:开启； false：关闭
	 */
	public void openOrCloseReadWrite(boolean enableReadWrite) {
		for (int i = 0; i < 10; i++) {
			this.enableReadWrite = enableReadWrite;
		}
	}

	private <T> List<List<T>> splitList(List<T> list, int pageSize) {
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

	/**
	 * 读写分离Support
	 */
	private class ReadWriteSupport {
		/**
		 * 权重比率池
		 * <p>
		 * 说明：根据数组元素的顺序，当{@link #weightRate}取相应值时，读写比例依次为{1:9, 2:8, 3:7, 4:6,
		 * 5:5, 6:4, 7:3, 8:2, 9:1}
		 * <p>
		 * 即:
		 * <p>
		 * 当{@link #readRate} = 1时，{@link #weightRate} =
		 * {@link #WEIGHT_RATE_POOL}[0]， 读写比例为1：9
		 * <p>
		 * 当{@link #readRate} = 2时，{@link #weightRate} =
		 * {@link #WEIGHT_RATE_POOL}[1]， 读写比例为2：8
		 * <p>
		 * 当{@link #readRate} = 3时，{@link #weightRate} =
		 * {@link #WEIGHT_RATE_POOL}[2]， 读写比例为3：7
		 * <p>
		 * ...........
		 * <p>
		 * 当{@link #readRate} = 9时，{@link #weightRate} =
		 * {@link #WEIGHT_RATE_POOL}[8]， 读写比例为9：1
		 * <p>
		 */
		private final int[] WEIGHT_RATE_POOL = { 0b0_000_000_001, 0b0_000_100_001, 0b0_001_001_001, 0b0_101_001_001,
				0b1_010_101_010, 0b1_010_101_011, 0b1_010_101_111, 0b1_011_101_111, 0b1_011_111_111 };
		/**
		 * 权重比率位置
		 */
		private volatile int weightRatePosition = 1;
		/**
		 * 权重比率
		 */
		private int weightRate = WEIGHT_RATE_POOL[0];

		/**
		 * 读取时是否走主库
		 * <p>
		 * 1. 默认读写分离配置未打开， 默认走主库
		 * <p>
		 * 2.
		 * {@link ReadServiceThreadContextHolder#hasReadServiceThreadContext()}
		 * 方法返回false，走主库
		 * <p>
		 * 3. 在Spring事务上下文中执行的查询走主库
		 * <p>
		 * 4. {@link ReadServiceThreadContextHolder#isRealtime()} 方法返回true，走主库
		 * <p>
		 * 5. {@link #isFromRead} 方法返回false，走主库
		 * <p>
		 */
		private boolean readFromMaster() {
			if (!enableReadWrite) {
				return true;
			}

			if (!ReadServiceThreadContextHolder.hasReadServiceThreadContext()) {
				// 进入到此分支，说明这个读操作一定来自于WriteService, 一定走主库;
				return true;
			}

			// 进入到下面所有分支的读操作，都来自于ReadService
			if (TransactionSynchronizationManager.isSynchronizationActive()) {
				return true;
			}

			if (ReadServiceThreadContextHolder.isRealtime()) {
				return true;
			}

			if (!this.isFromReadByWeight()) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 根据权重决定走读(从)库
		 * 
		 * @return true:走读(从)库; false:走写(主)库;
		 */
		private boolean isFromReadByWeight() {
			if (weightRatePosition > 0b1_000_000_000) {
				weightRatePosition = 1;
			}

			boolean read = (weightRate & weightRatePosition) == weightRatePosition;
			weightRatePosition = weightRatePosition << 1;
			return read;
		}

		/**
		 * 根据读比率设置权重值
		 * 
		 * @param readRate
		 *            读比率
		 */
		private void setWeight(int readRate) {
			if (readRate < 1 || readRate > this.WEIGHT_RATE_POOL.length) {
				String warnTip = "{readRate: " + readRate + " msg:Properties readRate is error!" +
						" Properties readRate should satisfy the conditions： 1 <= readRate <= " + this.WEIGHT_RATE_POOL.length + "}";
				log.warn(warnTip);

				readRate = 1;
			}
			weightRate = WEIGHT_RATE_POOL[readRate - 1];
		}
	}

	// setter方法
	public void setWriteTemplate(SqlSession writeTemplate) {
		this.writeTemplate = writeTemplate;
	}

	public void setReadTemplate(SqlSession readTemplate) {
		this.readTemplate = readTemplate;
	}

	public void setEnableReadWrite(boolean enableReadWrite) {
		this.enableReadWrite = enableReadWrite;
	}

	public void setReadRate(int readRate) {
		this.readRate = readRate;
	}

	public boolean isEnableReadWrite() {
		return enableReadWrite;
	}

	public int getReadRate() {
		return readRate;
	}

}