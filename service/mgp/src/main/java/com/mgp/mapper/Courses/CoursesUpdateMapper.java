package com.mgp.mapper.Courses;

import com.mgp.controller.model.Courses.CourseUpdateModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Courses.CourseUpdateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CoursesUpdateMapper {
    private final ModelMapper modelMapper;

    public CoursesUpdateMapper() { this.modelMapper = new ModelMapper(); }

    public CourseEntity convertDTOToEntity(CourseUpdateDTO courseUpdateDTO, List<ClassEntity> classEntityList) {

        return new CourseEntity(null, courseUpdateDTO.getName(), courseUpdateDTO.getShortName(), courseUpdateDTO.getNSemesters(), classEntityList);
    }

    public CourseUpdateDTO convertEntityToDTO(CourseEntity courseEntity) {

        List<Long> classesId = new ArrayList<>();

        courseEntity.getClasses().forEach(classEntity -> {
            classesId.add(classEntity.getId());
        });

        return new CourseUpdateDTO( courseEntity.getName(), courseEntity.getShortName(), courseEntity.getNSemesters(), classesId);
    }

    public CourseUpdateModel convertDTOToModel(CourseUpdateDTO courseUpdateDTO) { return modelMapper.map(courseUpdateDTO, CourseUpdateModel.class); }

    public CourseUpdateDTO convertModelToDTO(CourseUpdateModel courseUpdateModel) { return modelMapper.map(courseUpdateModel, CourseUpdateDTO.class); }

}
