package org.springboot.demo.pojo;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 复合主键
 * 方式1：
 *  在复合主键的类上使用 @Embeddable
 * 注意：复合主键必须实现序列化接口
 *  Composite-id class must implement Serializable: org.springboot.demo.pojo.CatId
 *
 *  方式2：
 *      使用@IdClass的方式，不标注@Embeddable
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
//@Embeddable
public class CatId implements Serializable {

    private Integer id;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
