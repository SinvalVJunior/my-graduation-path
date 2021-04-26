package com.mgp.controller;

import com.mgp.controller.model.ClassGetModel;
import com.mgp.controller.model.ClassModel;
import com.mgp.mapper.ClassesGetMapper;
import com.mgp.mapper.ClassesMapper;
import com.mgp.repository.entities.ClassEntity;
import com.mgp.service.ClassesService;
import com.mgp.service.dto.ClassDTO;
import com.mgp.service.dto.ClassGetDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/classes")
@RestController
public class ClassesController {

    public ClassesService classesService;
    private final ClassesMapper classesMapper;
    private final ClassesGetMapper classesGetMapper;

    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
        this.classesMapper = new ClassesMapper();
        this.classesGetMapper = new ClassesGetMapper();
    }

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

    @PostMapping
    public ResponseEntity<ClassModel> addClass(@RequestBody ClassModel classModel) {

        try {
            ClassDTO classResponse = classesService.addClass(classesMapper.convertModelToDTO(classModel));

            return ResponseEntity.ok(classesMapper.convertDTOToModel(classResponse));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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
