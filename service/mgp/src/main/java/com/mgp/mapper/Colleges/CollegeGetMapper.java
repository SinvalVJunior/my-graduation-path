package com.mgp.mapper.Colleges;

import com.mgp.controller.model.Colleges.CollegeGetModel;
import com.mgp.repository.entities.CollegeEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Colleges.CollegeGetDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CollegeGetMapper {
    private final ModelMapper modelMapper;

    public CollegeGetMapper() { this.modelMapper = new ModelMapper(); }

    public CollegeEntity convertDTOToEntity(CollegeGetDTO collegeDTO, List<CourseEntity> courseEntityList) {

        return new CollegeEntity(collegeDTO.getId(), collegeDTO.getName(), collegeDTO.getShortName(), collegeDTO.getState(), collegeDTO.getCity(), courseEntityList);
    }

    public CollegeGetDTO convertEntityToDTO(CollegeEntity collegeEntity) {
        List<Long> coursesId = new ArrayList<>();
        collegeEntity.getCourses().forEach(courseEntity -> {
            coursesId.add(courseEntity.getId());
        });

        return new CollegeGetDTO(collegeEntity.getId(), collegeEntity.getName(), collegeEntity.getShortName(), collegeEntity.getState(), collegeEntity.getCity(), coursesId);
    }

    public CollegeGetModel convertDTOToModel(CollegeGetDTO collegeDTO) { return modelMapper.map(collegeDTO, CollegeGetModel.class); }

    public CollegeGetDTO convertModelToDTO(CollegeGetModel collegeModel) { return modelMapper.map(collegeModel, CollegeGetDTO.class); }

}
