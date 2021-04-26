package com.mgp.mapper.Classes;


import com.mgp.controller.model.Classes.ClassCreateModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.Classes.ClassCreateDTO;
import org.modelmapper.ModelMapper;

public class ClassesMapper {
    private final ModelMapper modelMapper;

    public ClassesMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassCreateDTO classCreateDTO) {
        return modelMapper.map(classCreateDTO, ClassEntity.class);
    }

    public ClassCreateDTO convertEntityToDTO(ClassEntity classEntity) { return modelMapper.map(classEntity, ClassCreateDTO.class); }

    public ClassCreateModel convertDTOToModel(ClassCreateDTO classCreateDTO) { return modelMapper.map(classCreateDTO, ClassCreateModel.class); }

    public ClassCreateDTO convertModelToDTO(ClassCreateModel classCreateModel) { return modelMapper.map(classCreateModel, ClassCreateDTO.class); }

}
