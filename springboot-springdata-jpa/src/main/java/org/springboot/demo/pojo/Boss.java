package org.springboot.demo.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * boss
 *
 * @author wangwenjie
 * @date 2020-01-30
 */
@Entity
@Table(name = "boss")
@Getter
@Setter
public class Boss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "boss_name")
    private String bossName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boss", cascade = CascadeType.ALL)
    private List<Emp> empList;


}
