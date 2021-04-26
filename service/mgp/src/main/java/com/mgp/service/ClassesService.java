package com.mgp.service;

import com.mgp.mapper.ClassesGetMapper;
import com.mgp.repository.ClassesRepo;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.dto.ClassDTO;
import com.mgp.mapper.ClassesMapper;
import com.mgp.service.dto.ClassGetDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassesService {

    public ClassesRepo classesRepo;
    private final ClassesMapper classesMapper;
    private final ClassesGetMapper classesGetMapper;

    public ClassesService(ClassesRepo classesRepo) {
        this.classesRepo = classesRepo;
        this.classesMapper = new ClassesMapper();
        this.classesGetMapper = new ClassesGetMapper();
    }

    public List<ClassGetDTO> getClasses() {
        List<ClassEntity> classEntityList = (List<ClassEntity>) classesRepo.findAll();
        return classEntityList.stream()
                .map(classesGetMapper::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public ClassDTO addClass(ClassDTO classDTO) {
        ClassEntity classEntity = classesRepo.save(classesMapper.convertDTOToEntity(classDTO));
        return classesMapper.convertEntityToDTO(classEntity);
    }

    public void deleteClass(Long id) {
        classesRepo.deleteById(id);
    }
}
