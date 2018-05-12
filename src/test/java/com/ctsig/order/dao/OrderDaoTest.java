package com.ctsig.order.dao;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.ctsig.order.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * 测试用例
 *
 * @author wangan
 * @date 2018/3/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    /**
     * javax.transaction.Transactional 测试用例事物回滚
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void save() {
        Order order = new Order();
        order.setId(18010893384L);
        order.setExtraAmount(132.33);
        order.setExtraAmountType("0");
        order.setPayType("1");
        orderDao.save(order);
    }

    @Test
    public void test1() {
        Logger logger = LoggerFactory.getLogger(OrderDaoTest.class);
        logger.info("hello");
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(loggerContext);

    }
}