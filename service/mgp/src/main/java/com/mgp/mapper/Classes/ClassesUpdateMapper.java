package com.mgp.mapper.Classes;

import com.mgp.controller.model.Classes.ClassUpdateModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.Classes.ClassUpdateDTO;
import org.modelmapper.ModelMapper;

public class ClassesUpdateMapper {
    private final ModelMapper modelMapper;

    public ClassesUpdateMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassUpdateDTO classDTO) { return modelMapper.map(classDTO, ClassEntity.class); }

    public ClassUpdateDTO convertEntityToDTO(ClassEntity classEntity) { return modelMapper.map(classEntity, ClassUpdateDTO.class); }

    public ClassUpdateModel convertDTOToModel(ClassUpdateDTO classDTO) { return modelMapper.map(classDTO, ClassUpdateModel.class); }

    public ClassUpdateDTO convertModelToDTO(ClassUpdateModel classModel) { return modelMapper.map(classModel, ClassUpdateDTO.class); }

}
