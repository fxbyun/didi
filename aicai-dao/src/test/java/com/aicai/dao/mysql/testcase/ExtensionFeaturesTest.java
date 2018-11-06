package com.aicai.dao.mysql.testcase;

import static org.junit.Assert.*;

import java.util.Calendar;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.UserExtension;

@Ignore
/**
 * 
 * @project aicai-dao
 * @author zhengrong.xie
 * @date 2012-4-20
 * Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights reserved.
 */
@ContextConfiguration("classpath:/spring-extensionfeatures.xml")
public class ExtensionFeaturesTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private GenericDao userExtensionDao;
    private QueryRunner dbUnit;

    public void setUserExtensionDao(GenericDao userExtensionDao) {
        this.userExtensionDao = userExtensionDao;
    }

    @Before
    public void setUp() throws Exception {
        DataSource ds = (DataSource) applicationContext.getBean("dataSource");
        dbUnit = new QueryRunner(ds);
    }

    private UserExtension createUserExtension() {
        UserExtension userExtension = new UserExtension();

        userExtension.setName("测试用户名");
        userExtension.setPassword("测试密码");

        userExtension.setFeaturesVersion(100);

        return userExtension;
    }

    @Test
    public void testFull() throws Exception {
        prepareEnv();

        UserExtension model = createUserExtension();

        int count = userExtensionDao.insertAndSetupId("UserExtensionMapper.insert", model);

        assertTrue(count == 1);
        assert (model.getId() > 0);

        model = userExtensionDao.queryOne("UserExtensionMapper.selectByPrimaryKey", model.getId());

        assertTrue(model != null);
        assertTrue(model.getId() > 0);
        assertEquals("测试用户名", model.getName());

        model.setupFeature("id", 123);
        model.setupFeature("name", "双色球第2012102期");

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2011, 12, 12, 14, 0, 0);
        model.setupFeature("startTime", cal);

        count = userExtensionDao.updateByObj("UserExtensionMapper.update", model);

        assertTrue(count == 1);

        model = userExtensionDao.queryOne("UserExtensionMapper.selectByPrimaryKey", model.getId());

        assertTrue(model.getFeature("id", Integer.class) == 123);

        assertTrue(model.getFeature("startTime", Calendar.class).compareTo(cal) == 0);
        assertTrue("双色球第2012102期".equals(model.getFeature("name")));
        assertEquals("{\"id\":123,\"name\":\"双色球第2012102期\",\"startTime\":\"2012-01-12 14:00:00\"}",
                model.getFeatures());

        clearEnv(model.getId());
    }

    @Test
    public void testBigData() throws Exception {
        UserExtension userExtension = createUserExtension();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 25; i++) {
            userExtension.setupFeature("feature" + i, Calendar.getInstance());
        }

        int count = userExtensionDao.insertAndSetupId("UserExtensionMapper.insert", userExtension);

        long end = System.currentTimeMillis();

        assertTrue(count == 1);
        assertTrue((end - start) < 500);
    }

    private void clearEnv(long id) throws Exception {
        String sql = "delete from user_extension where id = ?";
        dbUnit.update(sql, id);
    }

    private void prepareEnv() throws Exception {
        String sql = "delete from user_extension";
        dbUnit.update(sql);
    }
}
