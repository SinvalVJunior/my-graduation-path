package com.mgp.controller.model.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassGetModel {
    private Long id;
    private String name;
    private Integer hours;
    private List<Long> dependencies;


}
