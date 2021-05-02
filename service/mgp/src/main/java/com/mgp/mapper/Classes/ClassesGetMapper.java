package com.mgp.mapper.Classes;

import com.mgp.controller.model.Classes.ClassGetModel;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.Classes.ClassCreateDTO;
import com.mgp.service.dto.Classes.ClassGetDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ClassesGetMapper {
    private final ModelMapper modelMapper;

    public ClassesGetMapper() { this.modelMapper = new ModelMapper(); }

    public ClassEntity convertDTOToEntity(ClassGetDTO classDTO, List<ClassEntity> dependencies) {
        return new ClassEntity(null, classDTO.getName(), classDTO.getHours(), dependencies);
    }

    public ClassGetDTO convertEntityToDTO(ClassEntity classEntity) {
        List<Long> dependencies = new ArrayList<>();
        for (ClassEntity dependency : classEntity.getDependencies()) {
            dependencies.add(dependency.getId());
        }
        return new ClassGetDTO(classEntity.getId(), classEntity.getName(), classEntity.getHours(), dependencies);
    }

    public ClassGetModel convertDTOToModel(ClassGetDTO classDTO) { return modelMapper.map(classDTO, ClassGetModel.class); }

    public ClassGetDTO convertModelToDTO(ClassGetModel classModel) { return modelMapper.map(classModel, ClassGetDTO.class); }

}
