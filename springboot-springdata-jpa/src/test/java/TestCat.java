import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.demo.JpaApplication;
import org.springboot.demo.dao.CatRepository;
import org.springboot.demo.pojo.Cat;
import org.springboot.demo.pojo.CatId;
import org.springboot.demo.utils.QueryRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * 测试复合主键的基本查询
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
@Slf4j
public class TestCat {

    @Autowired
    private CatRepository catRepository;

    @Test
    public void saveCat() {
        CatId catId = new CatId();
        catId.setId(1);
        catId.setType("黑猫");
        Cat cat = new Cat();
//        cat.setCatId(catId);
        cat.setName("黑猫警长");
        catRepository.save(cat);
    }

    /**
     * 复合主键查询方式 1 ：
     *
     * @author wangwenjie
     * @return void
     */
    @Test
    public void query1() {
        QueryRule<Cat> queryRule = QueryRule.getInstance();
        queryRule.addEqual("id", 1).addEqual("type", "黑猫");
        Optional<Cat> one = catRepository.findOne(queryRule);
        log.info("cat = {}", one.get());
    }

    //使用jpa
    @Test
    public void query2() {
        Cat cat = catRepository.findCatByIdAndType(1, "黑猫");
        System.out.println(cat);
    }

    //复合主键查询失败
    @Test
    public void query3() {
        List<Cat> list = catRepository.findAllByIdAndType(1, "黑猫");
        log.info("list = {}", list);
    }

}
