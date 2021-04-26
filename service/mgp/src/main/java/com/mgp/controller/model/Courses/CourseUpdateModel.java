package com.mgp.controller.model.Courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateModel {


    private String name;
    private String shortName;
    private Integer NSemesters;
    private List<Long> classesId;
}
