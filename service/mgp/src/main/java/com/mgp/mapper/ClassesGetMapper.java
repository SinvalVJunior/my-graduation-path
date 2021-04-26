package com.mgp.mapper;

import com.mgp.controller.model.ClassGetModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.ClassGetDTO;
import org.modelmapper.ModelMapper;

public class ClassesGetMapper {
    private final ModelMapper modelMapper;

    public ClassesGetMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassGetDTO classDTO) { return modelMapper.map(classDTO, ClassEntity.class); }

    public ClassGetDTO convertEntityToDTO(ClassEntity classEntity) { return modelMapper.map(classEntity, ClassGetDTO.class); }

    public ClassGetModel convertDTOToModel(ClassGetDTO classDTO) { return modelMapper.map(classDTO, ClassGetModel.class); }

    public ClassGetDTO convertModelToDTO(ClassGetModel classModel) { return modelMapper.map(classModel, ClassGetDTO.class); }

}
