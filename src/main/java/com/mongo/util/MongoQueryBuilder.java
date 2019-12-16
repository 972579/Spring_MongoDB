package com.mongo.util;

/**
 * mongodb查询工具类
 *
 * @author WangRui
 * @version 1.3.0
 * @date 2019/12/16 11:33
 * @since JDK 1.8
 */

public class MongoQueryBuilder<T> extends AbstractQuery<T, SerializableFunction<T, ?>, MongoQueryBuilder<T>> {

    public static <T> MongoQueryBuilder<T> build() {
        return new MongoQueryBuilder<>();
    }

}
