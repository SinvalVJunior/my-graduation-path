package com.mgp.service;

import com.mgp.mapper.ClassesGetMapper;
import com.mgp.mapper.ClassesUpdateMapper;
import com.mgp.repository.ClassesRepo;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.ClassCreateDTO;
import com.mgp.mapper.ClassesMapper;
import com.mgp.service.dto.ClassGetDTO;
import com.mgp.service.dto.ClassUpdateDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassesService {

    public ClassesRepo classesRepo;
    private final ClassesMapper classesMapper;
    private final ClassesGetMapper classesGetMapper;
    private final ClassesUpdateMapper classesUpdateMapper;

    public ClassesService(ClassesRepo classesRepo) {
        this.classesRepo = classesRepo;
        this.classesMapper = new ClassesMapper();
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
        ClassEntity classEntity = classesRepo.save(classesMapper.convertDTOToEntity(classCreateDTO));
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

        ClassEntity classEntitySaved = classesRepo.save(classEntity);

        return classesGetMapper.convertEntityToDTO(classEntitySaved);
    }
}
