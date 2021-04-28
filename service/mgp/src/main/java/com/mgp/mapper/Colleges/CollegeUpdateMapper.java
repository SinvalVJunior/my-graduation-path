package com.mgp.mapper.Colleges;

import com.mgp.controller.model.Colleges.CollegeUpdateModel;
import com.mgp.repository.entities.CollegeEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Colleges.CollegeUpdateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CollegeUpdateMapper {
    private final ModelMapper modelMapper;

    public CollegeUpdateMapper() { this.modelMapper = new ModelMapper(); }

    public CollegeEntity convertDTOToEntity(CollegeUpdateDTO collegeDTO, List<CourseEntity> courseEntityList) {

        return new CollegeEntity(collegeDTO.getId(), collegeDTO.getName(), collegeDTO.getShortName(), collegeDTO.getState(), collegeDTO.getCity(), courseEntityList);
    }

    public CollegeUpdateDTO convertEntityToDTO(CollegeEntity collegeEntity) {
        List<Long> coursesId = new ArrayList<>();
        collegeEntity.getCourses().forEach(courseEntity -> {
            coursesId.add(courseEntity.getId());
        });

        return new CollegeUpdateDTO(collegeEntity.getId(), collegeEntity.getName(), collegeEntity.getShortName(), collegeEntity.getState(), collegeEntity.getCity(), coursesId);
    }

    public CollegeUpdateModel convertDTOToModel(CollegeUpdateDTO collegeDTO) { return modelMapper.map(collegeDTO, CollegeUpdateModel.class); }

    public CollegeUpdateDTO convertModelToDTO(CollegeUpdateModel collegeModel) { return modelMapper.map(collegeModel, CollegeUpdateDTO.class); }
}
