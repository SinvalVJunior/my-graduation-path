package com.mgp.service;

import com.mgp.mapper.Students.StudentsCreateMapper;
import com.mgp.mapper.Students.StudentsGetMapper;
import com.mgp.repository.*;
import com.mgp.repository.entities.*;
import com.mgp.service.dto.Students.StudentCreateDTO;
import com.mgp.service.dto.Students.StudentGetDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentsService {

    private StudentsRepo studentsRepo;
    private SemestersRepo semestersRepo;
    private CollegesRepo collegesRepo;
    private CoursesRepo coursesRepo;
    private ClassesRepo classesRepo;

    private StudentsGetMapper studentsGetMapper;
    private StudentsCreateMapper studentsCreateMapper;

    public StudentsService(StudentsRepo studentsRepo, SemestersRepo semestersRepo, CollegesRepo collegesRepo, CoursesRepo coursesRepo, ClassesRepo classesRepo) {

        this.studentsRepo = studentsRepo;
        this.semestersRepo = semestersRepo;
        this.collegesRepo = collegesRepo;
        this.coursesRepo = coursesRepo;
        this.classesRepo = classesRepo;

        this.studentsGetMapper = new StudentsGetMapper();
        this.studentsCreateMapper = new StudentsCreateMapper();
    }

    public List<StudentGetDTO> getStudents() {

        List<StudentEntity> studentEntityList = studentsRepo.findAll();

        return studentEntityList.stream()
                .map(studentsGetMapper::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public StudentGetDTO createStudent(StudentCreateDTO studentCreateDTO) {

        List<SemesterEntity> semesterEntityList = new ArrayList<>();
        studentCreateDTO.getSemesters().forEach(semesterId -> {
            SemesterEntity semesterEntity = semestersRepo.findById(semesterId).orElseThrow(
                    () -> new EntityNotFoundException("Cannot find semester with id (" + semesterId + ").")
            );
            semesterEntityList.add(semesterEntity);
        });

        List<ClassEntity> classEntityList = new ArrayList<>();
        studentCreateDTO.getClassesDone().forEach(classId -> {
            ClassEntity classEntity = classesRepo.findById(classId).orElseThrow(
                    () -> new EntityNotFoundException("Cannot find class with id (" + classId + ").")
            );
            classEntityList.add(classEntity);
        });

        CollegeEntity collegeEntity = collegesRepo.findById(studentCreateDTO.getCollege()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find college with id (" + studentCreateDTO.getCollege() + ").")
        );

        CourseEntity courseEntity = coursesRepo.findById(studentCreateDTO.getCourse()).orElseThrow(
                () ->  new EntityNotFoundException("Cannot find course with id (" + studentCreateDTO.getCourse() + ").")
        );

        StudentEntity studentEntity = studentsCreateMapper.convertDTOToEntity(studentCreateDTO, semesterEntityList, collegeEntity, courseEntity, classEntityList);

        StudentEntity studentEntitySaved = studentsRepo.save(studentEntity);

        return studentsGetMapper.convertEntityToDTO(studentEntitySaved);

    }

    public void deleteStudent(Long id) {

        studentsRepo.deleteById(id);
    }


}