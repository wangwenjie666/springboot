package org.springboot.demo.dao;

import org.springboot.demo.pojo.UserInfo;
import org.springframework.data.jpa.repository.Query;

/**
 * userinfo dao
 *
 * @author wangwenjie
 * @date 2020-01-26
 */
public interface UserInfoRepository extends BaseRepository<UserInfo, Integer> {

//    @Query("select max(id) + 1 from UserInfo ")
//    int queryMaxId();
}
