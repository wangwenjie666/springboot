package org.springboot.demo.pojo;

import javax.persistence.*;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-01-31
 */
@Entity
@Table(name = "cat")
//标注复合主键实体类
@IdClass(CatId.class)
public class Cat {

    @Id
    private Integer id;
    @Id
    private String type;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
