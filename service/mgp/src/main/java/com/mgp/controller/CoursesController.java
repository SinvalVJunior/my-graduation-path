package com.mgp.controller;

import com.mgp.controller.model.Courses.CourseCreateModel;
import com.mgp.controller.model.Courses.CourseGetModel;
import com.mgp.controller.model.Courses.CourseUpdateModel;
import com.mgp.mapper.Courses.CoursesCreateMapper;
import com.mgp.mapper.Courses.CoursesGetMapper;
import com.mgp.mapper.Courses.CoursesUpdateMapper;
import com.mgp.service.CoursesService;
import com.mgp.service.dto.Courses.CourseGetDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"Courses"})
@RequestMapping("/api/v1/courses")
@RestController
public class CoursesController {
    private CoursesService coursesService;
    private CoursesCreateMapper coursesCreateMapper;
    private CoursesGetMapper coursesGetMapper;
    private CoursesUpdateMapper coursesUpdateMapper;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
        this.coursesCreateMapper = new CoursesCreateMapper();
        this.coursesGetMapper = new CoursesGetMapper();
        this.coursesUpdateMapper = new CoursesUpdateMapper();
    }

    @ApiOperation(value = "Retrieve all courses")
    @GetMapping
    public ResponseEntity<List<CourseGetModel>> getCourses() {

        try {
            List<CourseGetDTO> courseGetDTOList = coursesService.getCourses();
            List<CourseGetModel> courseGetModelList = courseGetDTOList.stream().map(coursesGetMapper::convertDTOToModel).collect(Collectors.toList());

            return ResponseEntity.ok(courseGetModelList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @ApiOperation(value = "Create a course")
    @PostMapping
    public ResponseEntity<CourseGetModel> addCourse(@RequestBody CourseCreateModel courseCreateModel) {

        try {
            CourseGetDTO courseGetDTO = coursesService.addCourse(coursesCreateMapper.convertModelToDTO(courseCreateModel));

            return ResponseEntity.ok(coursesGetMapper.convertDTOToModel(courseGetDTO));
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @ApiOperation(value = "Update a course")
    @PatchMapping("/{id}")
    public ResponseEntity<CourseGetModel> updateCourse(Long id, @RequestBody CourseUpdateModel courseUpdateModel) {

        try {
            CourseGetDTO courseGetDTO = coursesService.updateCourse( id, coursesUpdateMapper.convertModelToDTO(courseUpdateModel));

            return ResponseEntity.ok(coursesGetMapper.convertDTOToModel(courseGetDTO));
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @ApiOperation(value = "Delete a course")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {

        try {
            coursesService.deleteCourse(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Course with id " + id + " exists", e);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
