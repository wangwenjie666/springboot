package org.springboot.demo.dao;

import org.springboot.demo.pojo.Cat;
import org.springboot.demo.pojo.CatId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * cat dao
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public interface CatRepository extends BaseRepository<Cat, CatId> {

    //复合主键查询
    Cat findCatByIdAndType(Integer id, String type);

    List<Cat> findAllByIdAndType(Integer id, String type);

}
