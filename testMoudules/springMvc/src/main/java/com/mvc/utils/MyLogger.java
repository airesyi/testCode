package com.mvc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
    private static final Logger logger = LoggerFactory.getLogger(MyLogger.class);

    public static void main(String[] args) {
        if (logger.isDebugEnabled()) {
            logger.debug("123");
        }
        logger.debug("321");
        Integer.valueOf("a");
    }
}
