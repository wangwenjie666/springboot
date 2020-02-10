import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.demo.JpaApplication;
import org.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 事务回滚测试
 *
 * @author wangwenjie
 * @date 2020-02-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
@Slf4j
public class RollbackTest {

    @Autowired
    private UserService userService;

    @Test
    public void userTXRollBack(){
        userService.saveRollback();
    }
}
