package com.mgp.controller;

import com.mgp.controller.model.Classes.ClassGetModel;
import com.mgp.controller.model.Classes.ClassCreateModel;
import com.mgp.controller.model.Classes.ClassUpdateModel;
import com.mgp.mapper.Classes.ClassesGetMapper;
import com.mgp.mapper.Classes.ClassesCreateMapper;
import com.mgp.mapper.Classes.ClassesUpdateMapper;
import com.mgp.service.ClassesService;
import com.mgp.service.dto.Classes.ClassGetDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"Classes"})
@RequestMapping("/api/v1/classes")
@RestController
public class ClassesController {

    public ClassesService classesService;
    private final ClassesCreateMapper classesCreateMapper;
    private final ClassesGetMapper classesGetMapper;
    private final ClassesUpdateMapper classesUpdateMapper;

    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
        this.classesCreateMapper = new ClassesCreateMapper();
        this.classesGetMapper = new ClassesGetMapper();
        this.classesUpdateMapper = new ClassesUpdateMapper();
    }

    @ApiOperation(value = "Retrieve all classes")
    @GetMapping
    public ResponseEntity<List<ClassGetModel>> getClasses() {

        try {
            List<ClassGetDTO> classes = classesService.getClasses();
            List<ClassGetModel> classModelList = classes.stream().map(classesGetMapper::convertDTOToModel).collect(Collectors.toList());

            return ResponseEntity.ok(classModelList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Create a class")
    @PostMapping
    public ResponseEntity<ClassGetModel> addClass(@Valid @RequestBody ClassCreateModel classCreateModel, BindingResult result) {

        try {
            if (result.hasErrors())
                throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());

            ClassGetDTO classResponse = classesService.addClass(classesCreateMapper.convertModelToDTO(classCreateModel));

            return ResponseEntity.ok(classesGetMapper.convertDTOToModel(classResponse));
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


    @ApiOperation(value = "Update a class")
    @PatchMapping("/{id}")
    public ResponseEntity<ClassGetModel> updateClass(@PathVariable("id") @NotBlank Long id, @Valid @RequestBody ClassUpdateModel classUpdateModel, BindingResult result) {
        try {
            if (result.hasErrors())
                throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());

            ClassGetDTO classGetDTO = classesService.updateClass(id, classesUpdateMapper.convertModelToDTO(classUpdateModel));

            return ResponseEntity.ok(classesGetMapper.convertDTOToModel(classGetDTO));
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

    @ApiOperation(value = "Delete a class")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable("id") Long id) {
        try {
            classesService.deleteClass(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Class with id " + id + " exists", e);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
