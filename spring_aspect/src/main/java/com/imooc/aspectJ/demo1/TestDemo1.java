package com.imooc.aspectJ.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestDemo1 {

    @Resource(name = "productDao")
    private ProductDao productDao;

    @Test
    public void testDemo1(){
        productDao.save();
        productDao.delete();
        productDao.findOne();
        productDao.findAll();
        productDao.update();
    }
}
