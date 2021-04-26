package com.mgp.mapper;


import com.mgp.controller.model.ClassModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.ClassDTO;
import org.modelmapper.ModelMapper;

public class ClassesMapper {
    private final ModelMapper modelMapper;

    public ClassesMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassDTO classDTO) {
        return modelMapper.map(classDTO, ClassEntity.class);
    }

    public ClassDTO convertEntityToDTO(ClassEntity classEntity) { return modelMapper.map(classEntity, ClassDTO.class); }

    public ClassModel convertDTOToModel(ClassDTO classDTO) { return modelMapper.map(classDTO, ClassModel.class); }

    public ClassDTO convertModelToDTO(ClassModel classModel) { return modelMapper.map(classModel, ClassDTO.class); }

}
