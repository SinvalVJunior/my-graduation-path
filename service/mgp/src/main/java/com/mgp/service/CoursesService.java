package com.mgp.service;


import com.mgp.mapper.Courses.CoursesCreateMapper;
import com.mgp.mapper.Courses.CoursesGetMapper;
import com.mgp.mapper.Courses.CoursesUpdateMapper;
import com.mgp.repository.ClassesRepo;
import com.mgp.repository.CoursesRepo;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Courses.CourseCreateDTO;
import com.mgp.service.dto.Courses.CourseGetDTO;
import com.mgp.service.dto.Courses.CourseUpdateDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesService {

    private CoursesRepo coursesRepo;
    private ClassesRepo classesRepo;
    private CoursesCreateMapper coursesCreateMapper;
    private CoursesGetMapper coursesGetMapper;
    private CoursesUpdateMapper coursesUpdateMapper;

    public CoursesService(CoursesRepo coursesRepo, ClassesRepo classesRepo) {

        this.coursesRepo = coursesRepo;
        this.classesRepo = classesRepo;
        this.coursesCreateMapper = new CoursesCreateMapper();
        this.coursesGetMapper = new CoursesGetMapper();
        this.coursesUpdateMapper = new CoursesUpdateMapper();
    }

    public CourseGetDTO addCourse(CourseCreateDTO courseCreateDTO) {

        List<ClassEntity> classEntityList = new ArrayList<>();

        courseCreateDTO.getClassesId().forEach(classId -> {
            ClassEntity classEntity = classesRepo.findById(classId).orElseThrow(
                    () -> new EntityNotFoundException("Cannot find Class with id (" + classId + ")."));
            classEntityList.add(classEntity);
        });

        CourseEntity courseEntity = coursesCreateMapper.convertDTOToEntity(courseCreateDTO);

        classEntityList.forEach(classElement -> {
            courseEntity.getClasses().add(classElement);
        });
        CourseEntity courseEntityCreated = coursesRepo.save(courseEntity);

        return coursesGetMapper.convertEntityToDTO(courseEntity);
    }


    public List<CourseGetDTO> getCourses() {

        List<CourseEntity> courseEntityList = (List<CourseEntity>) coursesRepo.findAll();

        return courseEntityList.stream()
                .map(coursesGetMapper::convertEntityToDTO)
                .collect(Collectors.toList());

    }

    public void deleteCourse(Long id) {
        coursesRepo.deleteById(id);
    }

    public CourseGetDTO updateCourse(Long id, CourseUpdateDTO courseUpdateDTO) {

        CourseEntity courseEntity = coursesRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find Course with id (" + id + ")."));

        if(courseUpdateDTO.getClassesId() != null) {

            List<ClassEntity> classEntityList = new ArrayList<>();
            courseUpdateDTO.getClassesId().forEach(classId -> {
                ClassEntity classEntity = classesRepo.findById(classId).orElseThrow(() -> new EntityNotFoundException("Cannot find Class with id (" + classId + ")."));
                classEntityList.add(classEntity);
            });
            courseEntity.setClasses(classEntityList);
        }

        if(courseUpdateDTO.getName() != null)
            courseEntity.setName(courseUpdateDTO.getName());

        if(courseUpdateDTO.getShortName() != null)
            courseEntity.setShortName(courseUpdateDTO.getShortName());

        if(courseUpdateDTO.getNSemesters() != null)
            courseEntity.setNSemesters(courseUpdateDTO.getNSemesters());

        CourseEntity courseEntitySaved = coursesRepo.save(courseEntity);

        return coursesGetMapper.convertEntityToDTO(courseEntitySaved);
    }
}
