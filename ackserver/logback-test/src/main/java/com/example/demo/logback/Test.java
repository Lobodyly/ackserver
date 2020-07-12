package com.example.demo.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Test {


    public void test() {
        Logger logger= LoggerFactory.getLogger(Test.class);
        logger.info("logback.info 成功了");
        logger.error("logback.error 成功了");
        logger.warn("logback.warn 成功了");
    }
}
