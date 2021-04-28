package com.mgp.mapper.Colleges;

import com.mgp.controller.model.Colleges.CollegeCreateModel;
import com.mgp.repository.entities.CollegeEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Colleges.CollegeCreateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CollegeCreateMapper {
    private final ModelMapper modelMapper;

    public CollegeCreateMapper() { this.modelMapper = new ModelMapper(); }

    public CollegeEntity convertDTOToEntity(CollegeCreateDTO collegeDTO, List<CourseEntity> courseEntityList) {

        return new CollegeEntity(collegeDTO.getId(), collegeDTO.getName(), collegeDTO.getShortName(), collegeDTO.getState(), collegeDTO.getCity(), courseEntityList);
    }

    public CollegeCreateDTO convertEntityToDTO(CollegeEntity collegeEntity) {
        List<Long> coursesId = new ArrayList<>();
        collegeEntity.getCourses().forEach(courseEntity -> {
            coursesId.add(courseEntity.getId());
        });

        return new CollegeCreateDTO(collegeEntity.getId(), collegeEntity.getName(), collegeEntity.getShortName(), collegeEntity.getState(), collegeEntity.getCity(), coursesId);
    }

    public CollegeCreateModel convertDTOToModel(CollegeCreateDTO collegeDTO) { return modelMapper.map(collegeDTO, CollegeCreateModel.class); }

    public CollegeCreateDTO convertModelToDTO(CollegeCreateModel collegeModel) { return modelMapper.map(collegeModel, CollegeCreateDTO.class); }
}
