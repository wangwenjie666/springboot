package org.springboot.demo.utils;

import org.springframework.beans.BeanUtils;
import java.util.List;

/**
 * 复制bean 工具类
 *
 * @author wangwenjie
 * @date 2020-02-03
 */
public class BeanCopyUtils {

    public static <T> void copyPropertiesByList(List<T> sourceList, List<T> targetList) {
        for (T source : sourceList) {
            Class<?> classT = source.getClass();
            T target = null;
            try {
                target = (T) classT.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        }
    }
}
