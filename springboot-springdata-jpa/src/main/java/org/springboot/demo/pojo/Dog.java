package org.springboot.demo.pojo;

import javax.persistence.*;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
@Entity
@Table(name = "dog")
public class Dog {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "id")),
            @AttributeOverride(name = "type", column = @Column(name = "type"))
    })
    private DogId dogId;

    private String name;

    public DogId getDogId() {
        return dogId;
    }

    public void setDogId(DogId dogId) {
        this.dogId = dogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
