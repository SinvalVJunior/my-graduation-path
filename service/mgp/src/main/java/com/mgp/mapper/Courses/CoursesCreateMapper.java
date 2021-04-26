package com.mgp.mapper.Courses;

import com.mgp.controller.model.Courses.CourseCreateModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Courses.CourseCreateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CoursesCreateMapper {
    private final ModelMapper modelMapper;

    public CoursesCreateMapper() { this.modelMapper = new ModelMapper(); }

    public CourseEntity convertDTOToEntity(CourseCreateDTO courseCreateDTO) {
        return new CourseEntity(null, courseCreateDTO.getName(), courseCreateDTO.getShortName(), courseCreateDTO.getNSemesters(), new ArrayList<>());
    }

    public CourseCreateModel convertEntityToDTO(CourseEntity courseEntity) { return modelMapper.map(courseEntity, CourseCreateModel.class); }

    public CourseCreateModel convertDTOToModel(CourseCreateDTO courseCreateDTO) { return modelMapper.map(courseCreateDTO, CourseCreateModel.class); }

    public CourseCreateDTO convertModelToDTO(CourseCreateModel courseCreateModel) { return modelMapper.map(courseCreateModel, CourseCreateDTO.class); }

}
