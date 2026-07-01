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

/**
 * Core logger interface that serves as a facade for various logging implementations.
 *
 * <p>GodiKit Logger provides a unified logging API that can delegate to different
 * logging frameworks (Log4j2, Logback, System.out, etc.) based on the configured
 * provider.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * Logger logger = LoggerFactory.getLogger(MyClass.class);
 * logger.info("Application started at {}", LocalDateTime.now());
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-07 22:53
 */
public interface Logger {

    /**
     * Returns the facade name.
     *
     * @return the facade identifier (e.g., "godikit")
     */
    String getFacadeName();

    /**
     * Returns the underlying logger instance.
     *
     * @return the native logger object (e.g., org.slf4j.Logger, org.apache.logging.log4j.Logger)
     */
    Object getFacadeLogger();

    /**
     * Returns the provider name.
     *
     * @return the provider identifier (e.g., "log4j2", "logback", "system")
     */
    String getProviderName();

    boolean isTraceEnabled();

    boolean isDebugEnabled();

    boolean isInfoEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();

    void trace(final Throwable cause, final String msg, final Object... args);

    void trace(final Throwable cause);

    void trace(final String msg, final Object... args);

    void debug(final Throwable cause, final String msg, final Object... args);

    void debug(final Throwable cause);

    void debug(final String msg, final Object... args);

    void info(final Throwable cause, final String msg, final Object... args);

    void info(final Throwable cause);

    void info(final String msg, final Object... args);

    void warn(final Throwable cause, final String msg, final Object... args);

    void warn(final Throwable cause);

    void warn(final String msg, final Object... args);

    void error(final Throwable cause, final String msg, final Object... args);

    void error(final Throwable cause);

    void error(final String msg, final Object... args);
}
