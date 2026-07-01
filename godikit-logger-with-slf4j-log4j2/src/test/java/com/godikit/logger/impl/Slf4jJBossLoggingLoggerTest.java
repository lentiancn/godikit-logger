//package com.godikit.logger.impl;
//
//import org.jboss.logging.Logger;
//import org.junit.jupiter.api.Test;
//
//public class Slf4jJBossLoggingLoggerTest {
//
//    private static final Logger jbossLogger = Logger.getLogger(Slf4jJBossLoggingLoggerTest.class);
//
//    @Test
//    public void trace() {
//        jbossLogger.trace("JBoss-Logging-over-SLF4J trace message");
//    }
//
//    @Test
//    public void debug() {
//        jbossLogger.debug("JBoss-Logging-over-SLF4J debug message");
//    }
//
//    @Test
//    public void info() {
//        jbossLogger.info("JBoss-Logging-over-SLF4J info message");
//    }
//
//    @Test
//    public void warn() {
//        jbossLogger.warn("JBoss-Logging-over-SLF4J warn message");
//    }
//
//    @Test
//    public void error() {
//        jbossLogger.error("JBoss-Logging-over-SLF4J error message");
//    }
//
//    @Test
//    public void fatal() {
//        jbossLogger.fatal("JBoss-Logging-over-SLF4J fatal message");
//    }
//
//    @Test
//    public void logWithThrowable() {
//        Throwable cause = new RuntimeException("Test exception");
//        jbossLogger.error("JBoss-Logging-over-SLF4J log with throwable", cause);
//    }
//
//    @Test
//    public void logf() {
//        jbossLogger.infof("JBoss-Logging-over-SLF4J info with format: %s", "value");
//        jbossLogger.warnf("JBoss-Logging-over-SLF4J warn with format: %d", 123);
//        jbossLogger.errorf("JBoss-Logging-over-SLF4J error with format: %s", "error");
//    }
//
//    @Test
//    public void isTraceEnabled() {
//        if (jbossLogger.isTraceEnabled()) {
//            jbossLogger.trace("Trace is enabled");
//        }
//    }
//
//    @Test
//    public void isDebugEnabled() {
//        if (jbossLogger.isDebugEnabled()) {
//            jbossLogger.debug("Debug is enabled");
//        }
//    }
//
//    @Test
//    public void isInfoEnabled() {
//        if (jbossLogger.isInfoEnabled()) {
//            jbossLogger.info("Info is enabled");
//        }
//    }
//
//    @Test
//    public void isEnabledFor() {
//        if (jbossLogger.isEnabled(Logger.Level.DEBUG)) {
//            jbossLogger.debug("DEBUG level is enabled");
//        }
//        if (jbossLogger.isEnabled(Logger.Level.INFO)) {
//            jbossLogger.info("INFO level is enabled");
//        }
//    }
//
//    @Test
//    public void logv() {
//        jbossLogger.logv(Logger.Level.DEBUG, "JBoss-Logging-over-SLF4J logv message: {0}", "arg0");
//        jbossLogger.logv(Logger.Level.INFO, "JBoss-Logging-over-SLF4J logv with args: {0}, {1}", new Object[]{"a", "b"});
//    }
//
//    @Test
//    public void log() {
//        jbossLogger.log(Logger.Level.INFO, "JBoss-Logging-over-SLF4J log with Level.INFO");
//        jbossLogger.log(Logger.Level.ERROR, "JBoss-Logging-over-SLF4J log with Level.ERROR", new RuntimeException("Test"));
//    }
//
//    @Test
//    public void getLogger() {
//        Logger logger1 = Logger.getLogger("com.example.module");
//        Logger logger2 = Logger.getLogger(Slf4jJBossLoggingLoggerTest.class);
//        Logger logger3 = Logger.getLogger("com.example.service", "brand");
//    }
//}