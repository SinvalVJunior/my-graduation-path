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

        for (ClassEntity classEntity : allClasses) {

            if(!studentEntity.getClassesDone().contains(classEntity)) {

                if (studentHasAllClassDependencies(studentEntity, classEntity)) {

                    if (!classInSemesters(classEntity, studentEntity, classGetAvailableModel.getSemesterNumber())) {

                        availableClasses.add(classEntity);
                    }

                } else {

                    if(semestersHasAllDependencies(classEntity, studentEntity, classGetAvailableModel.getSemesterNumber())
                            && !classInSemesters(classEntity, studentEntity, classGetAvailableModel.getSemesterNumber())) {
                        availableClasses.add(classEntity);
                    }
                }

            }


        }

        return availableClasses.stream()
                .map(classesGetMapper::convertEntityToDTO)
                .collect(Collectors.toList());

    }

    private boolean semestersHasAllDependencies(ClassEntity classEntity, StudentEntity studentEntity, Integer semesterNumber) {

        boolean hasAllDependencies = false;

        for (ClassEntity dependency : classEntity.getDependencies()) {

            for (int i = 0; i < semesterNumber; i++) {

                if (studentEntity.getSemesters().get(i).getClasses().contains(dependency)) {
                    hasAllDependencies = true;
                    break;
                }
                else hasAllDependencies = false;


            }

            if(!hasAllDependencies)
                return false;

        }

        return hasAllDependencies;
    }

    public boolean studentHasAllClassDependencies( StudentEntity studentEntity, ClassEntity classEntity) {

        for (ClassEntity dependency : classEntity.getDependencies()) {

            if(!studentEntity.getClassesDone().contains(dependency))
                return false;

        }

        return true;
    }


    private boolean classInSemesters(ClassEntity classEntity, StudentEntity studentEntity, int semesterNumber) {

        boolean hasClass = false;

        for (int i = 0; i < semesterNumber; i++) {

            for (ClassEntity classSemester: studentEntity.getSemesters().get(i).getClasses()) {

                if(classSemester.getId().equals(classSemester.getId()))
                    hasClass = true;
            }
        }

        return hasClass;
    }
}
