package com.mgp.repository.entities;


import com.mgp.service.dto.ClassDTO;
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
    private int hours;

    @Column(name = "done")
    private boolean done;

    @Column(name = "added")
    private boolean added;

    @ManyToMany
    private List<ClassEntity> dependencies;


}
