package com.mgp.mapper.Students;

import com.mgp.controller.model.Students.StudentGetModel;
import com.mgp.repository.entities.CollegeEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.repository.entities.SemesterEntity;
import com.mgp.repository.entities.StudentEntity;
import com.mgp.service.dto.Students.StudentGetDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class StudentsGetMapper {

    private ModelMapper modelMapper;

    public StudentsGetMapper() {
        this.modelMapper = new ModelMapper();
    }

    public StudentEntity convertDTOToEntity(StudentGetDTO studentGetDTO, List<SemesterEntity> semesterEntityList, CollegeEntity collegeEntity, CourseEntity courseEntity) {

        return  new StudentEntity(studentGetDTO.getId(), studentGetDTO.getName(),collegeEntity, courseEntity, semesterEntityList);
    }

    public StudentGetDTO convertEntityToDTO(StudentEntity studentEntity) {

        Long college = studentEntity.getCollege().getId();
        Long course = studentEntity.getCourse().getId();

        List<Long> semesters = new ArrayList<>();

        studentEntity.getSemesters().forEach(semesterEntity -> {
            semesters.add(semesterEntity.getId());
        });

        return new StudentGetDTO(studentEntity.getId(), studentEntity.getName(), college, course, semesters);

    }

    public StudentGetModel convertDTOToModel(StudentGetDTO studentGetDTO) { return modelMapper.map(studentGetDTO, StudentGetModel.class); }
    public StudentGetDTO convertModelToDTO(StudentGetModel studentGetModel) { return modelMapper.map(studentGetModel, StudentGetDTO.class); }


}
