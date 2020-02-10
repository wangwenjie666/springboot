import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.demo.JpaApplication;
import org.springboot.demo.dao.DogRepository;
import org.springboot.demo.pojo.Dog;
import org.springboot.demo.pojo.DogId;
import org.springboot.demo.utils.QueryRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
@Slf4j
public class TestDog {

    @Autowired
    private DogRepository dogRepository;

    @Test
    public void saveDog() {
        DogId dogId = new DogId();
        dogId.setId(1);
        dogId.setType("白狗");

        Dog dog = new Dog();
        dog.setDogId(dogId);
        dog.setName("白皮狗");
        dogRepository.save(dog);
    }

    @Test
    public void query1() {
        DogId dogId = new DogId();
        dogId.setId(1);
        dogId.setType("白狗");
        Dog dogByDogId = dogRepository.findDogByDogId(dogId);
        log.info("dog = {}", dogByDogId);
    }

    @Test
    public void query2() {
        Dog dogByDogId = dogRepository.findDogByDogId_IdAndDogId_Type(1, "白狗");
        log.info("dog = {}", dogByDogId);
    }

    //不能实现
//    @Test
    public void query3() {
        QueryRule<Dog> queryRule = QueryRule.getInstance();
        queryRule.addEqual("id", 1)
                .addEqual("type", "白狗");
        Optional<Dog> one = dogRepository.findOne(queryRule);
        log.info("dog = {}", one.get());
    }

    @Test
    public void query4() {
        Dog dog = dogRepository.findDogByIdAndType(1, "白狗");
        log.info("dog = {}", dog);
    }

    @Test
    public void query5() {
        DogId dogId = new DogId();
        dogId.setId(1);
        dogId.setType("白狗");
        Optional<Dog> byId = dogRepository.findById(dogId);
        log.info("dog = {}", byId.get());
    }
}
