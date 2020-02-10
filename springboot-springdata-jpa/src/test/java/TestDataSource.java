import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.demo.JpaApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * 数据源测试
 *
 * @author wangwenjie
 * @date 2020-02-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
@Slf4j
public class TestDataSource {

//    @Autowired
    private final DataSource dataSource;

    public TestDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Test
    public void test01(){
        log.info("datasource = {}",dataSource);
    }

}
