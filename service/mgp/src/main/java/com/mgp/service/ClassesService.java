package com.mgp.service;

import com.mgp.controller.model.Classes.ClassGetAvailableModel;
import com.mgp.mapper.Classes.ClassesGetMapper;
import com.mgp.mapper.Classes.ClassesUpdateMapper;
import com.mgp.repository.ClassesRepo;
import com.mgp.repository.CoursesRepo;
import com.mgp.repository.StudentsRepo;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.repository.entities.StudentEntity;
import com.mgp.service.dto.Classes.ClassCreateDTO;
import com.mgp.mapper.Classes.ClassesCreateMapper;
import com.mgp.service.dto.Classes.ClassGetDTO;
import com.mgp.service.dto.Classes.ClassUpdateDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ClassesService {

    private ClassesRepo classesRepo;
    private StudentsRepo studentsRepo;
    private CoursesRepo coursesRepo;

    private final ClassesCreateMapper classesCreateMapper;
    private final ClassesGetMapper classesGetMapper;
    private final ClassesUpdateMapper classesUpdateMapper;

    public ClassesService(ClassesRepo classesRepo, StudentsRepo studentsRepo, CoursesRepo coursesRepo) {
        this.classesRepo = classesRepo;
        this.studentsRepo = studentsRepo;
        this.coursesRepo = coursesRepo;

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

        List<ClassEntity> dependencies = new ArrayList<>();

        for (Long dependencyId : classCreateDTO.getDependencies()) {
            ClassEntity dependency = classesRepo.findById(dependencyId).orElseThrow(
                    () -> new EntityNotFoundException("Cannot find class dependency with id (" + dependencyId + ").")
            );
            dependencies.add(dependency);
        }
        ClassEntity classEntity = classesRepo.save(classesCreateMapper.convertDTOToEntity( classCreateDTO, dependencies));
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

        if(classUpdateDTO.getDependencies() != null) {
            List<ClassEntity> dependencies = new ArrayList<>();
            for (Long dependencyId : classUpdateDTO.getDependencies()) {
                ClassEntity dependency = classesRepo.findById(dependencyId).orElseThrow(
                        () -> new EntityNotFoundException("Cannot find class dependency with id (" + dependencyId + ").")
                );
                dependencies.add(dependency);
            }
            classEntity.setDependencies(dependencies);
        }

        ClassEntity classEntitySaved = classesRepo.save(classEntity);

        return classesGetMapper.convertEntityToDTO(classEntitySaved);
    }

    public List<ClassGetDTO> getAvailableClasses(ClassGetAvailableModel classGetAvailableModel) {

        StudentEntity studentEntity = studentsRepo.findById(classGetAvailableModel.getStudentId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find Class with id (" + classGetAvailableModel.getStudentId() + ").")
        );

        CourseEntity courseEntity = coursesRepo.findById(studentEntity.getCourse().getId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find course with id (" + studentEntity.getCourse().getId() + ").")
        );

        List<ClassEntity> allClasses = courseEntity.getClasses();
        List<ClassEntity> availableClasses = new ArrayList<>();

        allClasses.forEach(classEntity -> {
            if(!studentEntity.getClassesDone().contains(classEntity)){

                AtomicBoolean hasAllDependencies = new AtomicBoolean(true);
                classEntity.getDependencies().forEach(dependency -> {
                    if(!studentEntity.getClassesDone().contains(dependency))
                        hasAllDependencies.set(false);
                });

                if(hasAllDependencies.get())
                    availableClasses.add(classEntity);

            }
        });


        return availableClasses.stream()
                .map(classesGetMapper::convertEntityToDTO)
                .collect(Collectors.toList());

    }
}
