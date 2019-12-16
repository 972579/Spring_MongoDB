package com.mongo.util;


import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lambda表达式序列化工具类
 *
 * @author WangRui
 * @version 1.3.0
 * @date 2019/12/16 11:35
 * @since JDK 1.8
 */
class SerializableUtil {

    private static Map<Class, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();

    private static final String GET = "get";
    private static final String IS = "is";

    /***
     * 转换方法引用为属性名
     * @param fn lambda表达式
     * @return lambda表达式的方法
     */
    static <T> String convertToFieldName(T fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        // 获取方法名
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith(GET)) {
            prefix = "get";
        } else if (methodName.startsWith(IS)) {
            prefix = "is";
        }
        if (prefix == null) {
            System.out.println("无效的getter方法: " + methodName);
        }
        // 截取get/is之后的字符串并转换首字母为小写
        return toLowerCaseFirstOne(methodName.replace(prefix, ""));
    }

    /**
     * 首字母转小写
     *
     * @param s 字符串
     * @return 转换后的字符串
     */
    private static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }


    /**
     * 序列化Lambda表达式
     */
    private static <T> SerializedLambda getSerializedLambda(T fn) {
        SerializedLambda lambda = CLASS_LAMBDA_CACHE.get(fn.getClass());
        // 先检查缓存中是否已存在
        if (lambda == null) {
            try {
                // 提取SerializedLambda并缓存
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMBDA_CACHE.put(fn.getClass(), lambda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lambda;
    }

}
