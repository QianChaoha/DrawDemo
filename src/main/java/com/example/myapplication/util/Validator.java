/**
 *
 */
package com.example.myapplication.util;

import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class name: VerdictUtils
 * class description: 判断类，判断空值
 * author: zp
 * create time: 2016-4-20 下午2:47:29
 * modify by:
 */
public class Validator {

    private static final String REG_PHONE_NUMBER = "^(1(3[0-9]|5[012356789]|7[678]|8[0-9]|4[57]))[0-9]{8}$";

//    private Validator() {
//        Preconditions.checkArgument(false, "Can not be instantiated!");
//    }

    public static <T> boolean isNull(@Nullable T reference) {
        return reference == null;
    }

    public static <T> boolean isNotNull(@Nullable T reference) {
        return reference != null;
    }

    public static <E> boolean isEmpty(@Nullable Collection<E> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    public static <E> boolean isNotEmpty(@Nullable Collection<E> collection) {
        return isNotNull(collection) && !collection.isEmpty();
    }

    public static <K, V> boolean isEmpty(@Nullable Map<K, V> map) {
        return isNull(map) || map.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(@Nullable Map<K, V> map) {
        return isNotNull(map) && !map.isEmpty();
    }

    public static boolean isEmpty(@Nullable CharSequence str) {
        return isNull(str) || str.length() == 0;
    }

    public static boolean isNotEmpty(@Nullable CharSequence str) {
        return isNotNull(str) && str.length() != 0;
    }

    public static <T> boolean isEmpty(@Nullable T[] t) {
        return isNull(t) || t.length == 0;
    }

    public static <T> boolean isNotEmpty(@Nullable T[] t) {
        return isNotNull(t) && t.length > 0;
    }

    /**
     * 验证手机号码
     *
     * @param str 字符串
     * @return true 是手机号
     */
    public static boolean isPhoneNumber(String str) {
        Pattern p = Pattern.compile(REG_PHONE_NUMBER);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}