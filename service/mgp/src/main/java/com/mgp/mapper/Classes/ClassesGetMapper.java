package com.mgp.mapper.Classes;

import com.mgp.controller.model.Classes.ClassGetModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.Classes.ClassGetDTO;
import org.modelmapper.ModelMapper;

public class ClassesGetMapper {
    private final ModelMapper modelMapper;

    public ClassesGetMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassGetDTO classDTO) { return modelMapper.map(classDTO, ClassEntity.class); }

    public ClassGetDTO convertEntityToDTO(ClassEntity classEntity) { return modelMapper.map(classEntity, ClassGetDTO.class); }

    public ClassGetModel convertDTOToModel(ClassGetDTO classDTO) { return modelMapper.map(classDTO, ClassGetModel.class); }

    public ClassGetDTO convertModelToDTO(ClassGetModel classModel) { return modelMapper.map(classModel, ClassGetDTO.class); }

}
