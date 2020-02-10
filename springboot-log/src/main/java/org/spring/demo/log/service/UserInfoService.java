package org.spring.demo.log.service;

import com.google.common.collect.Lists;
import org.spring.demo.log.dao.UserInfoDao;
import org.spring.demo.log.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * service
 *
 * @author wangwenjie
 * @date 2020-01-23
 */
@Service
@Transactional
public class UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    public void save() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("hh");
        userInfo.setAge(2);
        userInfoDao.save(userInfo);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserInfo> findAllByName(String name) {
        List list = userInfoDao.findAll(withNotNullName(name));
        return list;
    }

    /**
     * 非空姓名条件
     * @return
     */
    public Specification withNotNullName(String name) {
        return (Specification<UserInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            Path path = root.get("name");
            Predicate equalName = criteriaBuilder.equal(path, name);
            return criteriaBuilder.and(equalName);
        };
    }

    public List<UserInfo> findWithName(String name, String name2) {
        return userInfoDao.findAll(withName(name, name2));
    }

    /*public List<UserInfo> findWithName2(String name, String name2) {
        return userInfoDao.findAll(
                (equal("name", name).or(equal("name", name2))
                        .and((equal("age", 12).or(equal("age", 50))))));
    }*/

    /**
     * 查询特定姓名的人 or
     * @param name
     * @return
     */
    public Specification withName(String name, String name2) {
        return (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = Lists.newArrayList();
            Path pathName = root.get("name");
            Predicate equalOne = criteriaBuilder.equal(pathName, name);
            Predicate equalTwo = criteriaBuilder.equal(pathName, name2);
            list.add(equalOne);
            list.add(equalTwo);
            Predicate[] predicates = list.toArray(new Predicate[list.size()]);
            return criteriaBuilder.or(predicates);
        };
    }

    public Specification equal(String property, Object... variables) {
        return (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            Path path = root.get(property);
            Predicate predicate = null;
            for (Object variable : variables) {
                predicate = criteriaBuilder.or(criteriaBuilder.equal(path, variable));
            }
            return predicate;
        };
    }

    /*
    public Specification like(String property, Object variables) {
        return (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            Path path = root.get(property);
            Predicate predicate = criteriaBuilder.like(path, variables + "%");
            return predicate;
        };
    }

    public Specification in(String property, Object... variables) {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get(property);
                CriteriaBuilder.In in = criteriaBuilder.in(path);
                for (Object variable : variables) {
                    in.value(variable);
                }
                return in;
            }
        };
    }*/


}
