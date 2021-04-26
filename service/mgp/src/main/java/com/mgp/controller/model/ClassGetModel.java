package com.mgp.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassGetModel {
    private Long id;
    private String name;
    private Integer hours;
    private Boolean done;
    private Boolean added;

}
