/*
 * MIT License
 *
 * Copyright (c) 2026 Len (田隆)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.godikit.logger;

import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.utils.LoggerThrowableUtils;
import com.godikit.logger.utils.LoggerUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating and managing Logger instances.
 *
 * <p>This factory uses SPI (Service Provider Interface) to discover and load
 * Logger implementations from the classpath. It also supports caching of
 * Logger instances for better performance.</p>
 *
 * <h2>Usage:</h2>
 * <pre>{@code
 * Logger log = LoggerFactory.getLogger(MyClass.class);
 * LoggerFactory.setProvider("godikit", "logback");
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-07 22:53
 */
public final class LoggerFactory {

    private static final String PROVIDER_KEY_FORMAT = "%s+%s";
    private static final Map<String, Class<? extends Logger>> LOGGER_IMPL_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Logger> LOGGER_CACHE = new ConcurrentHashMap<>();
    private static volatile String currentFacade = NoOperationLoggerImpl.FACADE;
    private static volatile String currentProvider = NoOperationLoggerImpl.PROVIDER;

    static {
        loadLoggerImplementations();
    }

    private static void loadLoggerImplementations() {
        try {
            List<Class<? extends Logger>> loggerImplClasses = LoggerUtils.loadLoggerImplClasses(Logger.class);
            for (Class<? extends Logger> loggerImplClass : loggerImplClasses) {
                String facade = (String) loggerImplClass.getDeclaredField("FACADE").get(null);
                String provider = (String) loggerImplClass.getDeclaredField("PROVIDER").get(null);
                String key = String.format(PROVIDER_KEY_FORMAT, facade, provider);
                LOGGER_IMPL_MAP.put(key, loggerImplClass);
                if (currentFacade.equals(NoOperationLoggerImpl.FACADE)) {
                    currentFacade = facade;
                    currentProvider = provider;
                }
            }
        } catch (Throwable e) {
            System.err.println("[GodiKit Logger] Failed to load Logger implementations: " + LoggerThrowableUtils.toString(e));
        }
    }

    /**
     * Gets the current facade name.
     *
     * @return the current facade name
     */
    public static String getCurrentFacade() {
        return currentFacade;
    }

    /**
     * Gets the current provider in format "facade+provider".
     *
     * @return the current provider string
     */
    public static String getCurrentProvider() {
        return String.format(PROVIDER_KEY_FORMAT, currentFacade, currentProvider);
    }

    /**
     * Sets the current provider for logging.
     *
     * @param facade   the facade name (e.g., "godikit")
     * @param provider the provider name (e.g., "logback", "log4j2")
     */
    public static void setProvider(final String facade, final String provider) {
        String key = String.format(PROVIDER_KEY_FORMAT, facade, provider);
        if (LOGGER_IMPL_MAP.containsKey(key)) {
            currentFacade = facade;
            currentProvider = provider;
            LOGGER_CACHE.clear();
            return;
        }
        System.err.println("[GodiKit Logger] Provider not found: " + key);
    }

    /**
     * Gets a Logger instance by name.
     *
     * @param name the logger name
     * @return Logger instance (cached)
     */
    public static Logger getLogger(final String name) {
        return getLogger(name, false);
    }

    /**
     * Gets a Logger instance by class.
     *
     * @param clazz the class for the logger
     * @return Logger instance (cached)
     */
    public static Logger getLogger(final Class<?> clazz) {
        return getLogger(clazz.getName(), false);
    }

    /**
     * Gets a Logger instance by name with optional caching control.
     *
     * @param name      the logger name
     * @param createNew if true, creates a new instance bypassing cache
     * @return Logger instance
     */
    public static Logger getLogger(final String name, final boolean createNew) {
        if (!createNew) {
            Logger cached = LOGGER_CACHE.get(name);
            if (cached != null) {
                return cached;
            }
        }
        Logger logger = createLogger(name);
        if (!createNew) {
            LOGGER_CACHE.put(name, logger);
        }
        return logger;
    }

    /**
     * Gets a Logger instance by class with optional caching control.
     *
     * @param clazz     the class for the logger
     * @param createNew if true, creates a new instance bypassing cache
     * @return Logger instance
     */
    public static Logger getLogger(final Class<?> clazz, final boolean createNew) {
        return getLogger(clazz.getName(), createNew);
    }

    private static Logger createLogger(final String name) {
        String key = getCurrentProvider();
        Class<? extends Logger> loggerImplClass = LOGGER_IMPL_MAP.get(key);
        if (loggerImplClass == null) {
            return new NoOperationLoggerImpl(name);
        }
        try {
            Constructor<? extends Logger> constructor = loggerImplClass.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            return constructor.newInstance(name);
        } catch (Throwable e) {
            System.err.println("[GodiKit Logger] Failed to create Logger: " + LoggerThrowableUtils.toString(e));
            return new NoOperationLoggerImpl(name);
        }
    }
}
