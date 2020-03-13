package com.yuan.utils;

import android.util.Log;

/**
 * @author yuan
 */
public class LogUtils {
    public static final int LOG_LEVEL_NONE = 0;
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_WARN = 3;
    public static final int LOG_LEVEL_ERROR = 4;
    public static final int LOG_LEVEL_ALL = 5;

    private static int mLogLevel = LOG_LEVEL_INFO;

    public static int getLogLevel()
    {
        return mLogLevel;
    }
    public static void setLogLevel(int level)
    {
        LogUtils.mLogLevel = level;
    }

    /**
     * debug
     */
    public static void d(String tag, String msg)
    {
        if (getLogLevel() >= LOG_LEVEL_DEBUG)
        {
            Log.d(tag, msg);
        }
    }
    /**
     * info
     */
    public static void i(String tag, String msg)
    {
        if (getLogLevel() >= LOG_LEVEL_INFO)
        {
            Log.i(tag, msg);
        }
    }
    /**
     * warning
     */
    public static void w(String tag, String msg)
    {
        if (getLogLevel() >= LOG_LEVEL_WARN)
        {
            Log.w(tag, msg);
        }
    }
    /**
     * error
     */
    public static void e(String tag, String msg)
    {
        if (getLogLevel() >= LOG_LEVEL_ERROR)
        {
            Log.e(tag, msg);
        }
    }
    /**
     * verbose
     */
    public static void v(String tag, String msg)
    {
        if (getLogLevel() >= LOG_LEVEL_ALL)
        {
            Log.v(tag, msg);
        }
    }
}