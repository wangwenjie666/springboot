package org.springboot.demo.dao;

import org.springboot.demo.pojo.Cat;
import org.springboot.demo.pojo.CatId;
import org.springboot.demo.pojo.Dog;
import org.springboot.demo.pojo.DogId;
import org.springboot.demo.utils.QueryRule;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * cat dao
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public interface DogRepository extends BaseRepository<Dog, DogId> {

    Dog findDogByDogId_IdAndDogId_Type(Integer id, String type);

    Dog findDogByDogId(DogId dogId);

    @Query("select d from Dog d where d.dogId.id = ?1 and d.dogId.type = ?2")
    Dog findDogByIdAndType(Integer id, String type);

    @Override
    Optional<Dog> findById(DogId dogId);
}
