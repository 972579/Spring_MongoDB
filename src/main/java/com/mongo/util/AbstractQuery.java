package com.mongo.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

import static com.mongo.util.SerializableUtil.convertToFieldName;

/**
 * 封装mongodb 查询方法类
 *
 * @author WangRui
 * @version 1.3.0
 * @date 2019/12/16 11:51
 * @since JDK 1.8
 */
public abstract class AbstractQuery<T, R, Children extends AbstractQuery<T, R, Children>> {

    protected final Children typedThis = (Children) this;
    private Query query = new Query();

    /**
     * 精确匹配
     *
     * @param column 实体字段
     * @param val    值
     * @return 当前对象
     */
    public Children eq(R column, Object val) {
        query.addCriteria(Criteria.where(convertToFieldName(column)).is(val));
        return typedThis;
    }

    /**
     * 模糊匹配
     *
     * @param column 实体字段
     * @param value  值
     * @return 当前对象
     */
    public Children like(R column, Object value) {
        query.addCriteria(Criteria.where(convertToFieldName(column)).regex(value.toString()));
        return typedThis;
    }

    /**
     * 大于
     *
     * @param column 实体字段
     * @param value  值
     * @return 当前对象
     */
    public Children gt(R column, Object value) {
        query.addCriteria(Criteria.where(convertToFieldName(column)).gt(value));
        return typedThis;
    }

    /**
     * 大于等于
     *
     * @param column 实体字段
     * @param value  值
     * @return 当前对象
     */
    public Children ge(R column, Object value) {
        query.addCriteria(Criteria.where(convertToFieldName(column)).gte(value));
        return typedThis;
    }

    /**
     * 小于
     *
     * @param column 实体字段
     * @param value  值
     * @return 当前对象
     */
    public Children lt(R column, Object value) {
        query.addCriteria(Criteria.where(convertToFieldName(column)).lt(value));
        return typedThis;
    }

    /**
     * 小于等于
     *
     * @param column 实体字段
     * @param value  值
     * @return 当前对象
     */
    public Children le(R column, Object value) {
        query.addCriteria(Criteria.where(convertToFieldName(column)).lte(value));
        return typedThis;
    }

    /**
     * 根据字段值升序
     *
     * @param column 实体字段
     * @return 当前对象
     */
    public Children orderByAsc(R... column) {
        Optional.ofNullable(column).ifPresent(value -> query.with(Sort.by(Sort.Order.asc(convertToFieldName(value)))));
        return typedThis;
    }

    /**
     * 根据字段值降序
     *
     * @param column 实体字段
     * @return 当前对象
     */
    public Children orderByDesc(R column) {
        Optional.ofNullable(column).ifPresent(value -> query.with(Sort.by(Sort.Order.desc(convertToFieldName(value)))));
        return typedThis;
    }

    /**
     * 分页查询
     *
     * @param pageNum  页码
     * @param pageSize 页量
     * @return 当前对象
     */
    public Children pageList(int pageNum, int pageSize) {
        query.with(PageRequest.of((pageNum - 1) * pageSize, pageSize));
        return typedThis;
    }

    /**
     * @return 查询对象
     */
    public Query getQuery() {
        return query;
    }
}
