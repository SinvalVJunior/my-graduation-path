package com.mgp.repository.entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classes")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "added")
    private Boolean added;

    @ManyToMany
    private List<ClassEntity> dependencies;


}
