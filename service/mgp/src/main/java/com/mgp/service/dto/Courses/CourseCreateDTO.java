package com.mgp.service.dto.Courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateDTO {
    private String name;
    private String shortName;
    private Integer NSemesters;
    private List<Long> classesId;
}
