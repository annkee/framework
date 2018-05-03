package com.ctsig.order.dao.impl;

import com.ctsig.order.dao.BaseDao;
import com.ctsig.order.domain.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * @author wangan
 * @date 2018/5/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseDaoImplTest {

    @Autowired
    private BaseDao baseDao;

    @Test
    public void findById() {

        Object byId = baseDao.findById(new Country(), 1L);
    }

    @Test
    @Transactional
    public void save() {
        Country country = new Country();
        country.setCountryCode("qq");
        country.setCountryName("qq");
        boolean save = baseDao.save(country);

    }
}