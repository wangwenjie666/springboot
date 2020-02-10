package org.spring.demo.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * base dao
 *
 * Specifications可以使用编程的方式构建查询
 * 使用JpaSpecificationExecutor 扩展接口
 *
 * @author wangwenjie
 * @date 2020-01-24
 */
@NoRepositoryBean
public interface BaseRepository<DOAMIN, ID> extends JpaRepository<DOAMIN, ID>, JpaSpecificationExecutor<DOAMIN> {
}
