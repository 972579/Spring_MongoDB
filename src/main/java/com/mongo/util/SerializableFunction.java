package com.mongo.util;

import java.io.Serializable;

/**
 * 支持序列化的 Function
 *
 * @author WangRui
 * @version 1.3.0
 * @date 2019/12/16 11:31
 * @since JDK 1.8
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Serializable {
    /**
     * T convert R
     *
     * @param t
     * @return 返回值
     */
    R apply(T t);
}
