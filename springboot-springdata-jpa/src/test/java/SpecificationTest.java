import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.StringUtil;
import org.springboot.demo.JpaApplication;
import org.springboot.demo.dao.DogRepository;
import org.springboot.demo.dao.UserInfoRepository;
import org.springboot.demo.pojo.Boss;
import org.springboot.demo.pojo.Dog;
import org.springboot.demo.pojo.DogId;
import org.springboot.demo.pojo.UserInfo;
import org.springboot.demo.service.UserService;
import org.springboot.demo.utils.QueryRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * test
 *
 * @author wangwenjie
 * @date 2020-01-26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
@Slf4j
public class SpecificationTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 查询 或 条件
     */
    @Test
    public void test01() {
        Specification<UserInfo> spe = new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                Predicate p1 = criteriaBuilder.equal(name, "张麻子");
                Predicate p2 = criteriaBuilder.equal(name, "马冬梅");
                Predicate or = criteriaBuilder.or(p1, p2);
                Path<Object> age = root.get("age");
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(age);
                in.value(12);
                in.value(50);
                Predicate and = criteriaBuilder.and(or, in);
                return and;
            }
        };
        List<UserInfo> list = userInfoRepository.findAll(spe);
        log.info("list = {},size={}", list, list.size());
    }

    @Test
    public void test02() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addEqual("name", "张麻子", "马冬梅")
                .addIn("age", 12, 50, 11);
        List<UserInfo> list = userInfoRepository.findAll(queryRule);
        log.info("list = {},size={}", list, list.size());
    }


    //test in
    @Test
    public void test03() {
        Specification<UserInfo> specification = (Specification<UserInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> path = root.get("age");
            CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
            in.value(12);
            in.value(50);
            in.value(11);
            return in;
        };
        List<UserInfo> list = userInfoRepository.findAll(specification);
        log.info("list = {},size={}", list, list.size());

    }

    @Test
    public void test04() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addEqual("name", "马冬梅");
        /*List<UserInfo> list = userInfoRepository.findAll(queryRule.getSpecification());
        log.info("list = {},size={}", list, list.size());*/
    }

    @Test
    public void test05() {
        new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /**
                 * 使用criteriaBuilder构建条件
                 * 多个条件可以使用and or andnot 连接
                 */
                return criteriaBuilder.equal(root.get("name"), "张麻子");
            }
        };
    }

    @Test
    public void test06() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();

        queryRule.addEqual("name", "张麻子", "马冬梅");
        queryRule.addIn("age", 50, 11);
        List<UserInfo> list = userInfoRepository.findAll(queryRule);
        log.info("list = {},size={}", list, list.size());
//        log.info("==> paramtersMap = [{}]", queryRule.getParamtersMap());
    }

    @Test
    public void test07() {
        Specification<UserInfo> equal = new Specification<UserInfo>() {

            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                Predicate p1 = criteriaBuilder.equal(name, "张麻子");
                Predicate p2 = criteriaBuilder.equal(name, "马冬梅");
                Predicate or = criteriaBuilder.or(p1, p2);
                return or;
            }
        };
        Specification<UserInfo> in = new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> path = root.get("age");
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                in.value(12);
                in.value(50);
                in.value(11);
                return in;
            }
        };
        List<UserInfo> list = userInfoRepository.findAll(equal.and(null));
        log.info("list = {},size={}", list, list.size());
    }

    // test substring
    @Test
    public void test8() {
        Specification<UserInfo> spe = new Specification<UserInfo>() {

            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Expression<String> expression = root.get("name").as(String.class);

                return criteriaBuilder.equal(criteriaBuilder.substring(expression, 1, 1), "张");
            }
        };
        List<UserInfo> list = userInfoRepository.findAll(spe);
        log.info("list = {},size={}", list, list.size());
    }

    //使用queryRule substring
    @Test
    public void test9() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addSubstr("name", "事务", 1, 2);
        List<UserInfo> list = userInfoRepository.findAll(queryRule);
        log.info("list = {},size={}", list, list.size());
    }

    @Test
    public void test10() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("汤师爷");
        userInfo.setAge(12);
        userInfoRepository.save(userInfo);
    }

    @Test
    public void test11() {
        Specification<UserInfo> spe = new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                2020-01-28 16:44:44
                String d = "2020-01-28 16:44:44";
                String d2 = "2020-01-29 16:44:44";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Expression<Date> insertTime = root.get("insertTime").as(Date.class);
                    Date date = sdf.parse(d);
                    Date date2 = sdf.parse(d2);
                    return criteriaBuilder.between(insertTime, date, date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        List<UserInfo> list = userInfoRepository.findAll(spe);
        log.info("list = {},size={}", list, list.size());
    }

    @Test
    public void test12() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        String d = "2020-01-28 16:44:44";
        String d2 = "2020-01-29 16:44:44";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = sdf.parse(d);
            Date date2 = sdf.parse(d2);
            queryRule.addBetween("insertTime", date1, date2);
            List<UserInfo> list = userInfoRepository.findAll(queryRule);
            log.info("list = {},size={}", list, list.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test13() {
        Specification<UserInfo> specification = new Specification<UserInfo>() {

            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<String> path = root.get("name");
//                return criteriaBuilder.greaterThan(path, 1); // > 1
//                return criteriaBuilder.greaterThanOrEqualTo(path, 1); // >= 1
//                return criteriaBuilder.lessThan(path, 1);   // < 1
//                return criteriaBuilder.lessThanOrEqualTo(path, 1);  // <= 1
                return criteriaBuilder.like(path, "张%");
            }
        };
        List<UserInfo> list = userInfoRepository.findAll(specification);
        log.info("list = {},size={}", list, list.size());
    }

    //page
    @Test
    public void test14() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addEqual("");
        Page<UserInfo> page = userInfoRepository.findAll(queryRule,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")));
        List<UserInfo> list = page.getContent();
        log.info("list = {},size={}", list, list.size());
    }

    @Test
    public void test15() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addEqual("name", "张麻子");
//        List<UserInfo> list = userInfoRepository.findAll(queryRule, 2, 10).getContent();
        List<UserInfo> list = userInfoRepository.findAll(queryRule, PageRequest.of(0, 10)).getContent();
        log.info("list = {},size={}", list, list.size());
    }

    @Test
    public void test16() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        UserInfo userInfo = userInfoRepository.findOne(queryRule).get();
        Optional<UserInfo> u1 = Optional.ofNullable(userInfo);
    }

    @Autowired
    private UserService userService;

    @Test
    public void test17() {
        userService.save();
    }

    @Test
    public void test18() {
        Boss boss = userService.saveBoss();
        log.info(boss.getBossName());
    }


    @Test
    public void test19() {
        Boss boss = userService.queryBoss();
        log.info("boss = {}", boss);
    }

    @Test
    public void test20() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2020-01-30 21:42:05");
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addEqual("name", "张麻子")
                .addIn("id", 3, 4, 5, 6)
                .addBetween("id", 3, 6)
                .addLike("name", "马%")
                .addSubstr("name", "张", 1, 1)
                .addGreaterEqual("insertTime", date)
                .addAscOrder("id", "insertTime");
        List<UserInfo> list = userInfoRepository.findAll(queryRule, 1, 10).getContent();
    }

    @Autowired
    private DogRepository dogRepository;

    @Test
    public void test21() {
        DogId dogId = new DogId();
        dogId.setId(1);
        dogId.setType("白狗");

        Specification<Dog> spe = (Specification<Dog>) (root, query, criteriaBuilder) -> {
            String param = "dogId.id";
            if (param.contains(".")) {
                String[] _param = param.split("\\.");
                Path<Object> complexIdPath = root.get(_param[0]).get(_param[1]);
                return criteriaBuilder.equal(complexIdPath, dogId.getId());
            }
            return null;
        };
        List<Dog> list = dogRepository.findAll(spe);
        log.info("list = {}", list);
    }

    @Test
    public void test22() {
        DogId dogId = new DogId();
        dogId.setId(1);
        dogId.setType("白狗");

        QueryRule<Dog> queryRule = QueryRule.getInstance();
        queryRule.addEqual("dogId.id", 1);
        if (!StringUtils.isEmpty(dogId.getType())) {
            queryRule.addEqual("dogId.type", "白狗");
        }
        List<Dog> list = dogRepository.findAll(queryRule);
        log.info("list = {}", list);
    }

    @Test
    public void test23() {
        int i = userInfoRepository.queryMaxId();
        log.info("max id = {}", i);
    }

    @Test
    public void test24() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addLike("name", "张%", "马%");
        List<UserInfo> list = userInfoRepository.findAll(queryRule);
        log.info("list = {}", list);
    }

    @Test
    public void test25() throws SQLException {
        userService.testJdbcTemplate();
    }
}
