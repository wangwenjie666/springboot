package org.springboot.demo.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.util.*;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-01-26
 */
@Slf4j
public class QueryRule<T> {

    public static <T> QueryRule<T> getInstance() {
        return new QueryRule<T>();
    }

    //规范结果集
    private Specification<T> specification;

    //参数
    private Map<String, Object> paramtersMap = Maps.newHashMap();

    //排序规则
    private Sort sort;

    public Map<String, Object> getParamtersMap() {
        return paramtersMap;
    }

    public Specification<T> getSpecification() {
        return specification;
    }

    public Sort getSort() {
        return sort;
    }

    public QueryRule<T> addAscOrder(String... property) {
        this.sort = Sort.by(Sort.Direction.ASC, property);
        return this;
    }

    public QueryRule<T> addDescOrder(String... property) {
        this.sort = Sort.by(Sort.Direction.DESC, property);
        return this;
    }

    /**
     * addEqual
     *  where [property] = ( ? or ? or ? ...)
     *
     * @author wangwenjie
     * @param property
     * @param variables
     * @return org.springboot.demo.utils.QueryRule<T>
     */
    public QueryRule<T> addEqual(String property, Object... variables) {

        checkParamter(property, variables);

        Specification<T> specification = (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            if (property.contains(".")) {
                //复合主键处理
                String[] param = property.split("\\.");
                Path<Object> complexIdPath = root.get(param[0]).get(param[1]);
                setParamterMap(property, variables[0]);
                return criteriaBuilder.equal(complexIdPath, variables[0]);
            } else {
                Path<Object> path = root.get(property);
                int length = variables.length;
                if (length == 1) {
                    setParamterMap(property, variables[0]);
                    return criteriaBuilder.equal(path, variables[0]);
                } else {
                    List<Predicate> list = Lists.newArrayList();
                    for (Object object : variables) {
                        setParamterMap(property, object);
                        Predicate predicate = criteriaBuilder.equal(path, object);
                        list.add(predicate);
                    }
                    return criteriaBuilder.or(list.toArray(new Predicate[0]));
                }
            }
        };
        this.and(specification);
        return this;
    }

    private static void checkParamter(String property, Object[] variables) {
        Assert.hasText(property, "property can not be null or empty");
        Assert.notEmpty(variables, "variables can not be null or empty");
    }

    /**
     * 查询参数
     * @param property
     * @param object
     */
    private void setParamterMap(String property, Object object) {
        Object value = paramtersMap.get(property);
        if (value != null) {
            paramtersMap.put(property, value + "," + object);
        } else {
            paramtersMap.put(property, object);
        }
    }

    /**
     * like xx or xx
     * @param property
     * @param variables
     * @return
     */
    public QueryRule<T> addLike(String property, String... variables) {
        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Path<String> path = root.get(property);
            if (variables.length == 1) {
                setParamterMap(property + "_like", variables[0]);
                return criteriaBuilder.like(path, variables[0]);
            } else {
                List<Predicate> list = Lists.newArrayList();
                for (String variable : variables) {
                    setParamterMap(property + "_like", variable);
                    list.add(criteriaBuilder.like(path, variable));
                }
                return criteriaBuilder.or(list.toArray(new Predicate[0]));
            }
        };
        this.and(specification);
        return this;
    }

    /**
     * addIn
     *  where id in (?,?...)
     * @param property
     * @param variables
     * @return
     */
    public QueryRule<T> addIn(String property, Object... variables) {

        checkParamter(property, variables);

        Specification<T> specification = (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> path = root.get(property);
            CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
            for (Object variable : variables) {
                setParamterMap(property + "_in", variable);
                in.value(variable);
            }
            return in;
        };
        this.and(specification);
        return this;
    }

    /**
     * 使用截取字符串进行比较
     *
     * 执行sql：where substring(userinfo0_.name, 1, 2)=?
     *
     *  select substring('abcdef',1,2)
     *      mysql 'ab'
     *      postgresql 'ab'
     *      todo informix
     *  select substring('abcdef',0,2)
     *      mysql null
     *      todo postgresql
     *      todo informix
     * @param property
     * @param variable
     * @param start
     * @param length
     * @return
     */
    public QueryRule<T> addSubstr(String property, Object variable, int start, int length) {
        setParamterMap("substring(" + property + "," + start + "," + length + ")", variable);

        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Expression<String> expression = root.get(property).as(String.class);
            Expression<String> substring = criteriaBuilder.substring(expression, start, length);
            return criteriaBuilder.equal(substring, variable);
        };
        this.and(specification);
        return this;
    }

    /**
     * where xxx between ? and ?
     * @param property
     * @param variables
     * @return
     */
    public <E extends Comparable<? super E>> QueryRule<T> addBetween(String property, E... variables) {
        int length = variables.length;
        Assert.isTrue(length == 2, "variables length must be 2");
        Assert.isTrue(variables[0].getClass() == variables[1].getClass(), "variables type must be the same");

        setParamterMap(property + "_between", variables[0] + " and " + variables[1]);

        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Path<E> path = root.get(property);
            return criteriaBuilder.between(path, variables[0], variables[1]);
        };
        this.and(specification);
        return this;
    }

    /**
     * where xxx > ?
     * @param property
     * @param variables
     * @param <E>
     * @return
     */
    public <E extends Comparable<? super E>> QueryRule<T> addGreaterThan(String property, E variables) {
        setParamterMap(property + "_gt", " ( > ) " + variables);
        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Path<E> path = root.get(property);
            return criteriaBuilder.greaterThan(path, variables);
        };
        this.and(specification);
        return this;
    }


    /**
     * where xxx >= ?
     * @param property
     * @param variables
     * @param <E>
     * @return
     */
    public <E extends Comparable<? super E>> QueryRule<T> addGreaterEqual(String property, E variables) {
        setParamterMap(property + "_ge", " ( >= ) " + variables);
        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Path<E> path = root.get(property);
            return criteriaBuilder.greaterThanOrEqualTo(path, variables);
        };
        this.and(specification);
        return this;
    }

    /**
     * where xxx < ?
     * @param property
     * @param variables
     * @param <E>
     * @return
     */
    public <E extends Comparable<? super E>> QueryRule<T> addLessThan(String property, E variables) {
        setParamterMap(property + "_lt", " ( < ) " + variables);
        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Path<E> path = root.get(property);
            return criteriaBuilder.lessThan(path, variables);
        };
        this.and(specification);
        return this;
    }

    /**
     * where xxx <= ?
     * @param property
     * @param variables
     * @param <E>
     * @return
     */
    public <E extends Comparable<? super E>> QueryRule<T> addLessEqual(String property, E variables) {
        setParamterMap(property + "_le", " ( <= ) " + variables);
        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Path<E> path = root.get(property);
            return criteriaBuilder.lessThanOrEqualTo(path, variables);
        };
        this.and(specification);
        return this;
    }


    /**
     * and连接
     * @param specification
     */
    private void and(Specification<T> specification) {
        this.specification = specification.and(this.specification);
    }

    private void or(Specification<T> specification) {
        this.specification = specification.or(this.specification);
    }

    private QueryRule() {

    }
}
