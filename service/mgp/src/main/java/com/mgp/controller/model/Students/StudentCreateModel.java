package com.mgp.controller.model.Students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateModel {

    private String name;
    private Long college;
    private Long course;
    private List<Long> semesters;
    private List<Long> classesDone;

}
