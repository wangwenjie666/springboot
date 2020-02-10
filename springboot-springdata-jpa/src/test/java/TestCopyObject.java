import com.google.common.collect.Lists;
import org.junit.Test;
import org.springboot.demo.utils.BeanCopyUtils;

import java.util.List;

/**
 * 对象拷贝
 *
 * @author wangwenjie
 * @date 2020-02-03
 */
public class TestCopyObject {
    @Test
    public void test01() {
        long begin = System.currentTimeMillis();
        SourceObj source = new SourceObj();
        ComplexId complexId = new ComplexId();
        complexId.setId(10);
        complexId.setType("type1");
        source.setId(complexId);
        source.setName("张麻子");

        List<CS> csList = Lists.newArrayList();
        CS cs1 = new CS("张三丰");
        CS cs2 = new CS("马冬梅");
        csList.add(cs1);
        csList.add(cs2);
        source.setList(csList);
        List<SourceObj> sourceObjList = Lists.newArrayList();
        List<SourceObj> targetObjList = Lists.newArrayList();
        sourceObjList.add(source);
        sourceObjList.add(source);
        BeanCopyUtils.copyPropertiesByList(sourceObjList, targetObjList);
        System.out.println(System.currentTimeMillis() - begin);//286
    }

    @Test
    public void test02() throws IllegalAccessException, InstantiationException {
        SourceObj so = new SourceObj();

        Class<? extends SourceObj> aClass = so.getClass();
        aClass.newInstance();
    }
}







