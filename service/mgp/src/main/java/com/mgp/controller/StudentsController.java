package com.mgp.controller;

import com.mgp.controller.model.Students.StudentCreateModel;
import com.mgp.controller.model.Students.StudentGetModel;
import com.mgp.mapper.Students.StudentsCreateMapper;
import com.mgp.mapper.Students.StudentsGetMapper;
import com.mgp.service.StudentsService;
import com.mgp.service.dto.Students.StudentCreateDTO;
import com.mgp.service.dto.Students.StudentGetDTO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"Students"})
@RequestMapping("/api/v1/students")
@RestController
public class StudentsController {

    private StudentsService studentsService;
    private StudentsGetMapper studentsGetMapper;
    private StudentsCreateMapper studentCreateMapper;

    public StudentsController(StudentsService studentsService) {

        this.studentsService = studentsService;

        this.studentsGetMapper = new StudentsGetMapper();
        this.studentCreateMapper = new StudentsCreateMapper();
    }

    @GetMapping
    public ResponseEntity<List<StudentGetModel>> getStudents() {

        List<StudentGetDTO> studentGetDTOList = studentsService.getStudents();

        List<StudentGetModel> studentGetModelList = studentGetDTOList.stream()
                                                        .map(studentsGetMapper::convertDTOToModel)
                                                        .collect(Collectors.toList());

        return ResponseEntity.ok(studentGetModelList);
    }

    @PostMapping
    public ResponseEntity<StudentGetModel> createStudents(@RequestBody StudentCreateModel studentCreateModel) {
        StudentGetDTO studentGetDTO = studentsService.createStudent(studentCreateMapper.convertModelToDTO(studentCreateModel));
        StudentGetModel studentGetModel = studentsGetMapper.convertDTOToModel(studentGetDTO);

        return ResponseEntity.ok(studentGetModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudents(@PathVariable("id") Long id) {

        studentsService.deleteStudent(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
