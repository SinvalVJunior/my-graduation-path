package com.mgp.service;

import com.mgp.mapper.Students.StudentsCreateMapper;
import com.mgp.mapper.Students.StudentsGetMapper;
import com.mgp.repository.CollegesRepo;
import com.mgp.repository.CoursesRepo;
import com.mgp.repository.SemestersRepo;
import com.mgp.repository.StudentsRepo;
import com.mgp.repository.entities.CollegeEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.repository.entities.SemesterEntity;
import com.mgp.repository.entities.StudentEntity;
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
    private StudentsGetMapper studentsGetMapper;
    private StudentsCreateMapper studentsCreateMapper;
    private SemestersRepo semestersRepo;
    private CollegesRepo collegesRepo;
    private CoursesRepo coursesRepo;

    public StudentsService(StudentsRepo studentsRepo, SemestersRepo semestersRepo, CollegesRepo collegesRepo, CoursesRepo coursesRepo) {

        this.studentsRepo = studentsRepo;
        this.semestersRepo = semestersRepo;
        this.collegesRepo = collegesRepo;
        this.coursesRepo = coursesRepo;

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

        CollegeEntity collegeEntity = collegesRepo.findById(studentCreateDTO.getCollege()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find college with id (" + studentCreateDTO.getCollege() + ").")
        );

        CourseEntity courseEntity = coursesRepo.findById(studentCreateDTO.getCourse()).orElseThrow(
                () ->  new EntityNotFoundException("Cannot find course with id (" + studentCreateDTO.getCourse() + ").")
        );

        StudentEntity studentEntity = studentsCreateMapper.convertDTOToEntity(studentCreateDTO, semesterEntityList, collegeEntity, courseEntity);

        StudentEntity studentEntitySaved = studentsRepo.save(studentEntity);

        return studentsGetMapper.convertEntityToDTO(studentEntitySaved);

    }

    public void deleteStudent(Long id) {

        studentsRepo.deleteById(id);
    }


}
