package com.mgp.service;

import com.mgp.mapper.Classes.ClassesGetMapper;
import com.mgp.mapper.Classes.ClassesUpdateMapper;
import com.mgp.repository.ClassesRepo;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.Classes.ClassCreateDTO;
import com.mgp.mapper.Classes.ClassesCreateMapper;
import com.mgp.service.dto.Classes.ClassGetDTO;
import com.mgp.service.dto.Classes.ClassUpdateDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassesService {

    public ClassesRepo classesRepo;
    private final ClassesCreateMapper classesCreateMapper;
    private final ClassesGetMapper classesGetMapper;
    private final ClassesUpdateMapper classesUpdateMapper;

    public ClassesService(ClassesRepo classesRepo) {
        this.classesRepo = classesRepo;
        this.classesCreateMapper = new ClassesCreateMapper();
        this.classesGetMapper = new ClassesGetMapper();
        this.classesUpdateMapper = new ClassesUpdateMapper();
    }

    public List<ClassGetDTO> getClasses() {
        List<ClassEntity> classEntityList = (List<ClassEntity>) classesRepo.findAll();
        return classEntityList.stream()
                .map(classesGetMapper::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public ClassGetDTO addClass(ClassCreateDTO classCreateDTO) {
        ClassEntity classEntity = classesRepo.save(classesCreateMapper.convertDTOToEntity(classCreateDTO));
        return classesGetMapper.convertEntityToDTO(classEntity);
    }

    public void deleteClass(Long id) {
        classesRepo.deleteById(id);
    }

    public ClassGetDTO updateClass(Long id, ClassUpdateDTO classUpdateDTO) {
        ClassEntity classEntity = classesRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find Class with id (" + id + ").")
        );

        if(classUpdateDTO.getName() != null)
            classEntity.setName(classUpdateDTO.getName());

        if(classUpdateDTO.getHours() != null)
            classEntity.setHours(classUpdateDTO.getHours());

        if(classUpdateDTO.getAdded() != null)
            classEntity.setAdded(classUpdateDTO.getAdded());

        if(classUpdateDTO.getDone() != null)
            classEntity.setDone(classUpdateDTO.getDone());

        ClassEntity classEntitySaved = classesRepo.save(classEntity);

        return classesGetMapper.convertEntityToDTO(classEntitySaved);
    }
}
