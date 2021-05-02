package com.mgp.mapper.Students;

import com.mgp.controller.model.Students.StudentCreateModel;
import com.mgp.repository.entities.*;
import com.mgp.service.dto.Students.StudentCreateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class StudentsCreateMapper {
    private ModelMapper modelMapper;

    public StudentsCreateMapper() {
        this.modelMapper = new ModelMapper();
    }

    public StudentEntity convertDTOToEntity(StudentCreateDTO studentCreateDTO, List<SemesterEntity> semesterEntityList, CollegeEntity collegeEntity, CourseEntity courseEntity, List<ClassEntity> classesDone) {

        return  new StudentEntity( null, studentCreateDTO.getName(),collegeEntity, courseEntity, semesterEntityList, classesDone);
    }

    public StudentCreateDTO convertEntityToDTO(StudentEntity studentEntity) {

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


        return new StudentCreateDTO( studentEntity.getName(), college, course, semesters, classesDone);

    }

    public StudentCreateModel convertDTOToModel(StudentCreateDTO studentCreateDTO) { return modelMapper.map(studentCreateDTO, StudentCreateModel.class); }
    public StudentCreateDTO convertModelToDTO(StudentCreateModel studentCreateModel) { return modelMapper.map(studentCreateModel, StudentCreateDTO.class); }

}
