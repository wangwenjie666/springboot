package org.spring.demo.log.dao;

import org.spring.demo.log.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * userinfo dao
 *
 * @author wangwenjie
 * @date 2020-01-23
 */
public interface UserInfoDao extends BaseRepository<UserInfo, Integer> {
}
