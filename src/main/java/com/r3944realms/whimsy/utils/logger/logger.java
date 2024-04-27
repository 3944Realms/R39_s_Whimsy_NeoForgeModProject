package com.r3944realms.whimsy.utils.logger;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import org.slf4j.Marker;

public final class logger {
    /*info*/
    private static final Logger logger = LogUtils.getLogger();
    public static void info() {
        logger.info("[Whimsy] I am here !!!");
    }
    public static void info(String inf) {
        logger.info(inf);
    }
    public static void info(String inf,Object... args) {
        logger.info(inf, args);
    }
    public static void info(String msg, Throwable t) {
        logger.info(msg, t);
    }
    public static void info(Marker mar, String msg) {
        logger.info(mar, msg);
    }
    public static void info(Marker mar, String form, Object... args) {
        logger.info(mar, form, args);
    }
    public static void info(Marker mar, String form, Throwable t) {
        logger.info(mar, form, t);
    }
    /*debug*/
    public static void debug() {
        logger.debug("[Whimsy] debug here !!!");
    }
    public static void debug(String inf) {
        logger.debug(inf);
    }
    public static void debug(String inf,Object... args) {
        logger.debug(inf, args);
    }
    public static void debug(String msg, Throwable t) {
        logger.debug(msg, t);
    }
    public static void debug(Marker mar, String msg) {
        logger.debug(mar, msg);
    }
    public static void debug(Marker mar, String form, Object... args) {
        logger.debug(mar, form, args);
    }
    public static void debug(Marker mar, String form, Throwable t) {
        logger.debug(mar, form, t);
    }
    /*error*/
    public static void error() {
        logger.error("[Whimsy] emm... ... a error occurred.");
    }
    public static void error(String msg) {
        logger.error(msg);
    }
    public static void error(String msg, Object... args) {
        logger.error(msg, args);
    }
    public static void error(Marker mar, String form, Object... args) {
        logger.error(mar, form, args);
    }
    public static void error(String msg, Throwable t) {
        logger.error(msg, t);
    }
    public static void error(Marker mar, String msg) {
        logger.error(mar, msg);
    }
    public static void error(Marker mar, String form, Throwable t) {
        logger.error(mar, form, t);
    }
    /*Enough*/
}
