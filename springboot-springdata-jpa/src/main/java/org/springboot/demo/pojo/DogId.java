package org.springboot.demo.pojo;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
@Embeddable
public class DogId implements Serializable {

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

    @Override
    public String toString() {
        return "DogId{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
