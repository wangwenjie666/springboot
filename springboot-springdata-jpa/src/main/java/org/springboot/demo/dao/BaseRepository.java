package org.springboot.demo.dao;

import org.springboot.demo.utils.QueryRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * base
 *
 * @author wangwenjie
 * @date 2020-01-26
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    default List<T> findAll(QueryRule<T> queryRule) {
        return findAll(queryRule.getSpecification());
    }

    default Page<T> findAll(QueryRule<T> queryRule, Pageable pageable) {
        return findAll(queryRule.getSpecification(), pageable);
    }

    default Page<T> findAll(QueryRule<T> queryRule, int pageNo, int pageSize) {
        if (queryRule.getSort() != null) {
            return findAll(queryRule, PageRequest.of(pageNo - 1, pageSize, queryRule.getSort()));
        }
        return findAll(queryRule, PageRequest.of(pageNo - 1, pageSize));
    }

    default Optional<T> findOne(QueryRule<T> queryRule) {
        return findOne(queryRule.getSpecification());
    }

    @Query("select max(id) + 1 from #{#entityName}")
    int queryMaxId();
}
