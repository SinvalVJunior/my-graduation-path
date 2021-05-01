package com.mgp.mapper.Students;

import com.mgp.controller.model.Students.StudentCreateModel;
import com.mgp.repository.entities.CollegeEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.repository.entities.SemesterEntity;
import com.mgp.repository.entities.StudentEntity;
import com.mgp.service.dto.Students.StudentCreateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class StudentsCreateMapper {
    private ModelMapper modelMapper;

    public StudentsCreateMapper() {
        this.modelMapper = new ModelMapper();
    }

    public StudentEntity convertDTOToEntity(StudentCreateDTO studentCreateDTO, List<SemesterEntity> semesterEntityList, CollegeEntity collegeEntity, CourseEntity courseEntity) {

        return  new StudentEntity( null, studentCreateDTO.getName(),collegeEntity, courseEntity, semesterEntityList);
    }

    public StudentCreateDTO convertEntityToDTO(StudentEntity studentEntity) {

        Long college = studentEntity.getCollege().getId();
        Long course = studentEntity.getCourse().getId();

        List<Long> semesters = new ArrayList<>();

        studentEntity.getSemesters().forEach(semesterEntity -> {
            semesters.add(semesterEntity.getId());
        });

        return new StudentCreateDTO( studentEntity.getName(), college, course, semesters);

    }

    public StudentCreateModel convertDTOToModel(StudentCreateDTO studentCreateDTO) { return modelMapper.map(studentCreateDTO, StudentCreateModel.class); }
    public StudentCreateDTO convertModelToDTO(StudentCreateModel studentCreateModel) { return modelMapper.map(studentCreateModel, StudentCreateDTO.class); }

}
