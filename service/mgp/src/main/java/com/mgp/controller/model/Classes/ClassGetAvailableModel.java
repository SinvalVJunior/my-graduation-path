package com.mgp.controller.model.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassGetAvailableModel {

    private Long studentId;
    private Integer semesterNumber;
}
