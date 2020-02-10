package org.springboot.demo.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * emp
 *
 * @author wangwenjie
 * @date 2020-01-30
 */
@Entity
@Table(name = "emp")
@Getter
@Setter
public class Emp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "emp_name")
    private String empName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss")
    private Boss boss;
}
