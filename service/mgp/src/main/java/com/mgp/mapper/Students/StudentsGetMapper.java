package com.mgp.mapper.Students;

import com.mgp.controller.model.Students.StudentGetModel;
import com.mgp.repository.entities.*;
import com.mgp.service.dto.Students.StudentGetDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class StudentsGetMapper {

    private ModelMapper modelMapper;

    public StudentsGetMapper() {
        this.modelMapper = new ModelMapper();
    }

    public StudentEntity convertDTOToEntity(StudentGetDTO studentGetDTO, List<SemesterEntity> semesterEntityList, CollegeEntity collegeEntity, CourseEntity courseEntity, List<ClassEntity> classEntityList) {

        return  new StudentEntity(studentGetDTO.getId(), studentGetDTO.getName(),collegeEntity, courseEntity, semesterEntityList, classEntityList);
    }

    public StudentGetDTO convertEntityToDTO(StudentEntity studentEntity) {

        Long college = studentEntity.getCollege().getId();
        Long course = studentEntity.getCourse().getId();

        List<Long> semesters = new ArrayList<>();
        List<Long> classesDone = new ArrayList<>();

        studentEntity.getSemesters().forEach(semesterEntity -> {
            semesters.add(semesterEntity.getId());
        });

        studentEntity.getClassesDone().forEach(classEntity -> {
            classesDone.add(classEntity.getId());
        });

        return new StudentGetDTO(studentEntity.getId(), studentEntity.getName(), college, course, semesters, classesDone);

    }

    public StudentGetModel convertDTOToModel(StudentGetDTO studentGetDTO) { return modelMapper.map(studentGetDTO, StudentGetModel.class); }
    public StudentGetDTO convertModelToDTO(StudentGetModel studentGetModel) { return modelMapper.map(studentGetModel, StudentGetDTO.class); }


}
