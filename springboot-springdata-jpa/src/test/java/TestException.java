import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * 异常测试
 *
 * @author wangwenjie
 * @date 2020-02-03
 */
@Slf4j
public class TestException {

    @Test
    public void test01() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
            e.printStackTrace();
            String ex = getEx(e);
            log.info("e.message = {}", e.getMessage());
            log.info("ex = {}", ex);
        }
    }

    public String getEx(Throwable cause) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            cause.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    public String errorFn() {
        String msg = "";
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            msg = "c";
            throw e;
        }
        return msg;
    }

    @Test
    public void test02() {
        String s = errorFn();
        log.info("s = {}", s);
    }


    @Test
    public void test03() {
        String str[] = {"a", "b", "c"};
        System.out.println(Arrays.toString(str));
    }
}
