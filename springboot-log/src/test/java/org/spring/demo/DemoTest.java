package org.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-01-23
 */
@Slf4j
public class DemoTest {
    @Test
    public void test01() {
        MyBoolean myBoolean = new MyBoolean();
        System.out.println(myBoolean.isFlag());
    }

    @Test
    public void test02() {
        try {
            String string = null;
            string.equals("aa");
            throw new IllegalArgumentException("arguments");
        } catch (IllegalArgumentException | NullPointerException e) {
            log.info(e.getMessage());
        }
    }

    @Test
    public void test03() {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void test04() {
        LinkedList<Object> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void test05() {
        Date date = new Date();
        Date d = (Date) date;
        log.info("date = {},d = {}", date, d);
    }

    @Test
    public void test6() {
        Integer i = new Integer(1);
        boolean b = i instanceof Integer;
        boolean b1 = i instanceof Number;
        boolean b2 = i instanceof Comparable;
        log.info("b = {},b1 = {},b2 = {}", b, b1, b2);//都为true
        Comparable i1 = i;
        log.info(i1.toString());
    }

    @Test
    public void test7() {

        a(10);

    }

    public <E> void a(E variable) {
        Class classt = variable.getClass();
        ParameterizedType type = (ParameterizedType) classt.getGenericSuperclass();
        log.info("classt = {},type = {}", classt, type);
    }

    @Test
    public void test8() {
        String str = "a";

        int i = str.indexOf('%');
        log.debug("i = {}", i);  // -1
    }

}

class MyBoolean {
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}