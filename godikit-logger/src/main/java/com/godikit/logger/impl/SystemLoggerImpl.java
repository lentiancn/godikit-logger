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
package com.godikit.logger.impl;

import com.godikit.logger.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Logger implementation using System.out/System.err.
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-22 09:19
 */
public class SystemLoggerImpl implements Logger {

    public static final String FACADE = "godikit";
    public static final String PROVIDER = "system";

    public SystemLoggerImpl(final String name) {
    }

    public SystemLoggerImpl(final Class<?> clazz) {
    }

    @Override
    public String getFacadeName() {
        return FACADE;
    }

    @Override
    public Object getFacadeLogger() {
        return this;
    }

    @Override
    public String getProviderName() {
        return PROVIDER;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void trace(final Throwable cause, final String msg, final Object... args) {
        log(cause, msg, args, false);
    }

    @Override
    public void trace(final Throwable cause) {
        trace(cause, null, (Object) null);
    }

    @Override
    public void trace(final String msg, final Object... args) {
        trace(null, msg, args);
    }

    @Override
    public void debug(final Throwable cause, final String msg, final Object... args) {
        log(cause, msg, args, false);
    }

    @Override
    public void debug(final Throwable cause) {
        debug(cause, null, (Object) null);
    }

    @Override
    public void debug(final String msg, final Object... args) {
        debug(null, msg, args);
    }

    @Override
    public void info(final Throwable cause, final String msg, final Object... args) {
        log(cause, msg, args, false);
    }

    @Override
    public void info(final Throwable cause) {
        info(cause, null, (Object) null);
    }

    @Override
    public void info(final String msg, final Object... args) {
        info(null, msg, args);
    }

    @Override
    public void warn(final Throwable cause, final String msg, final Object... args) {
        log(cause, msg, args, true);
    }

    @Override
    public void warn(final Throwable cause) {
        warn(cause, null, (Object) null);
    }

    @Override
    public void warn(final String msg, final Object... args) {
        warn(null, msg, args);
    }

    @Override
    public void error(final Throwable cause, final String msg, final Object... args) {
        log(cause, msg, args, true);
    }

    @Override
    public void error(final Throwable cause) {
        error(cause, null, (Object) null);
    }

    @Override
    public void error(final String msg, final Object... args) {
        error(null, msg, args);
    }

    private void log(final Throwable cause, final String msg, final Object[] args, final boolean isErr) {
        StringBuilder sb = new StringBuilder();
        if (msg != null) {
            sb.append(formatMessage(msg, args));
        }
        if (cause != null) {
            sb.append(throwableToString(cause));
        }
        if (isErr) {
            System.err.println(sb);
        } else {
            System.out.println(sb);
        }
    }

    private String formatMessage(final String msg, final Object... args) {
        if (args == null || args.length == 0) {
            return msg;
        }
        StringBuilder result = new StringBuilder(msg.length() + args.length * 10);
        int placeholderIndex = 0;
        int lastIndex = 0;
        while (placeholderIndex < args.length) {
            int next = msg.indexOf("{}", lastIndex);
            if (next == -1) {
                break;
            }
            result.append(msg, lastIndex, next);
            result.append(args[placeholderIndex++]);
            lastIndex = next + 2;
        }
        result.append(msg.substring(lastIndex));
        return result.toString();
    }

    private String throwableToString(final Throwable throwable) {
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        } catch (Throwable e) {
            return throwable.toString();
        }
    }
}
