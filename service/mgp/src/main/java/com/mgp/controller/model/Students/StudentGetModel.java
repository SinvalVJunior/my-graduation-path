package com.mgp.controller.model.Students;

import com.mgp.repository.entities.SemesterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGetModel {

    private Long id;
    private String name;
    private Long college;
    private Long course;
    private List<SemesterEntity> semesters;
    private List<Long> classesDone;

}
