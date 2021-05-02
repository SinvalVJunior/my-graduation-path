package com.mgp.mapper.Classes;


import com.mgp.controller.model.Classes.ClassCreateModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.Classes.ClassCreateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ClassesCreateMapper {
    private final ModelMapper modelMapper;

    public ClassesCreateMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassCreateDTO classCreateDTO, List<ClassEntity> dependencies) {

        return new ClassEntity(null, classCreateDTO.getName(), classCreateDTO.getHours(), dependencies);

    }

    public ClassCreateDTO convertEntityToDTO(ClassEntity classEntity) {

        List<Long> dependencies = new ArrayList<>();
        for (ClassEntity dependency : classEntity.getDependencies()) {
            dependencies.add(dependency.getId());
        }
        return new ClassCreateDTO(classEntity.getName(), classEntity.getHours(), dependencies);
    }

    public ClassCreateModel convertDTOToModel(ClassCreateDTO classCreateDTO) { return modelMapper.map(classCreateDTO, ClassCreateModel.class); }

    public ClassCreateDTO convertModelToDTO(ClassCreateModel classCreateModel) { return modelMapper.map(classCreateModel, ClassCreateDTO.class); }

}
