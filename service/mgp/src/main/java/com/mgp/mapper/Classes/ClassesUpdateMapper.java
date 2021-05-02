package com.mgp.mapper.Classes;

import com.mgp.controller.model.Classes.ClassUpdateModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.Classes.ClassGetDTO;
import com.mgp.service.dto.Classes.ClassUpdateDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ClassesUpdateMapper {
    private final ModelMapper modelMapper;

    public ClassesUpdateMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassUpdateDTO classDTO, List<ClassEntity> dependencies) {
        return new ClassEntity(null, classDTO.getName(), classDTO.getHours(), dependencies);
    }

    public ClassUpdateDTO convertEntityToDTO(ClassEntity classEntity) {
        List<Long> dependencies = new ArrayList<>();
        for (ClassEntity dependency : classEntity.getDependencies()) {
            dependencies.add(dependency.getId());
        }
        return new ClassUpdateDTO( classEntity.getName(), classEntity.getHours(), dependencies);
    }

    public ClassUpdateModel convertDTOToModel(ClassUpdateDTO classDTO) { return modelMapper.map(classDTO, ClassUpdateModel.class); }

    public ClassUpdateDTO convertModelToDTO(ClassUpdateModel classModel) { return modelMapper.map(classModel, ClassUpdateDTO.class); }

}
