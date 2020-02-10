package org.springboot.demo.mapdemo;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-01-27
 */
public class MapTest {
    @Test
    public void test01() {
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put(new A("张三"), "张三");
        Object obj = map.get(new A("张三"));
        /**
         * 如果不重写hashcode方法 ，获取的是null，
         * 两个实例会放到不同的hash桶中
         */
        System.out.println(obj);
    }
}

class A {

    public A() {
    }

    public A(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //google提供的重写equals和hashcode的方式
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        A a = (A) o;
        return Objects.equal(name, a.name);
    }

    /*@Override
    public int hashCode() {
        return Objects.hashCode(name);
    }*/
}
