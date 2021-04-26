package com.mgp.mapper.Courses;

import com.mgp.controller.model.Courses.CourseGetModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Courses.CourseGetDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CoursesGetMapper {

    private final ModelMapper modelMapper;

    public CoursesGetMapper() { this.modelMapper = new ModelMapper(); }

    public CourseEntity convertDTOToEntity(CourseGetDTO courseGetDTO, List<ClassEntity> classEntityList) {

        return new CourseEntity(courseGetDTO.getId(), courseGetDTO.getName(), courseGetDTO.getShortName(), courseGetDTO.getNSemesters(), classEntityList);
    }

    public CourseGetDTO convertEntityToDTO(CourseEntity courseEntity) {

        List<Long> classesId = new ArrayList<>();

        courseEntity.getClasses().forEach(classEntity -> {
            classesId.add(classEntity.getId());
        });

        return new CourseGetDTO(courseEntity.getId(), courseEntity.getName(), courseEntity.getShortName(), courseEntity.getNSemesters(), classesId);
    }

    public CourseGetModel convertDTOToModel(CourseGetDTO courseGetDTO) { return modelMapper.map(courseGetDTO, CourseGetModel.class); }

    public CourseGetDTO convertModelToDTO(CourseGetModel courseGetModel) { return modelMapper.map(courseGetModel, CourseGetDTO.class); }

}
