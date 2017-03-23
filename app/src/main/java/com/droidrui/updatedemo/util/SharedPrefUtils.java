package com.droidrui.updatedemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.droidrui.updatedemo.App;
import com.droidrui.updatedemo.constant.KEY;

import java.util.Map;

/**
 * SharedPreferences的工具类
 */
public class SharedPrefUtils {

    private static final String PUBLIC_FILE = "public_file";

    public static void putToPublicFile(String key, Object object) {
        put(PUBLIC_FILE, key, object);
    }

    public static Object getFromPublicFile(String key, Object defaultObject) {
        return get(PUBLIC_FILE, key, defaultObject);
    }

    public static void putToUserFile(String key, Object object) {
        put(getUserFileName(), key, object);
    }

    public static Object getFromUserFile(String key, Object defaultObject) {
        return get(getUserFileName(), key, defaultObject);
    }

    private static String getUserFileName() {
        return "user_" + getFromPublicFile(KEY.UID, 0L) + "_file";
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    private static void put(String fileName, String key, Object object) {
        if (object == null) {
            return;
        }
        SharedPreferences sp = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    private static Object get(String fileName, String key, Object defaultObject) {
        if (defaultObject == null) {
            throw new NullPointerException("需要指定默认值");
        }
        SharedPreferences sp = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值对应的值
     */
    private static void remove(String fileName, String key) {
        SharedPreferences sp = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();
    }

    /**
     * 清除所有数据
     */
    private static void clear(String fileName) {
        SharedPreferences sp = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    /**
     * 查询某个key是否已经存在
     */
    private static boolean contains(String fileName, String key) {
        SharedPreferences sp = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    private static Map<String, ?> getAll(String fileName) {
        SharedPreferences sp = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

}
