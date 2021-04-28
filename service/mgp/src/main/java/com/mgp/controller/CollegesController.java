package com.mgp.controller;

import com.mgp.controller.model.Colleges.CollegeCreateModel;
import com.mgp.controller.model.Colleges.CollegeGetModel;
import com.mgp.controller.model.Colleges.CollegeUpdateModel;
import com.mgp.mapper.Colleges.CollegeCreateMapper;
import com.mgp.mapper.Colleges.CollegeGetMapper;
import com.mgp.mapper.Colleges.CollegeUpdateMapper;
import com.mgp.service.CollegesService;
import com.mgp.service.dto.Colleges.CollegeGetDTO;
import com.mgp.service.dto.Colleges.CollegeUpdateDTO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"Colleges"})
@RequestMapping("/api/v1/colleges")
@RestController
public class CollegesController {

    private CollegesService collegesService;
    private CollegeGetMapper collegeGetMapper;
    private CollegeCreateMapper collegeCreateMapper;
    private CollegeUpdateMapper collegeUpdateMapper;

    public CollegesController(CollegesService collegesService) {
        this.collegesService = collegesService;
        this.collegeGetMapper = new CollegeGetMapper();
        this.collegeCreateMapper = new CollegeCreateMapper();
        this.collegeUpdateMapper = new CollegeUpdateMapper();
    }

    @GetMapping
    public ResponseEntity<List<CollegeGetModel>> getColleges() {
        List<CollegeGetDTO> collegeGetDTOList = collegesService.getColleges();
        List<CollegeGetModel> collegeGetModelList = collegeGetDTOList.stream()
                                                        .map(collegeGetMapper::convertDTOToModel)
                                                        .collect(Collectors.toList());

        return ResponseEntity.ok(collegeGetModelList);

    }

    @PostMapping
    public ResponseEntity<CollegeGetModel> addCollege(@RequestBody CollegeCreateModel collegeCreateModel) {

        CollegeGetDTO collegeGetDTO = collegesService.addCollege(collegeCreateMapper.convertModelToDTO(collegeCreateModel));

        return ResponseEntity.ok(collegeGetMapper.convertDTOToModel(collegeGetDTO));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<CollegeGetModel> updateCollege(@PathVariable("id") Long id, @RequestBody CollegeUpdateModel collegeUpdateModel) {

        CollegeUpdateDTO collegeUpdateDTO = collegeUpdateMapper.convertModelToDTO(collegeUpdateModel);

        CollegeGetDTO collegeGetDTO = collegesService.updateCollege( id, collegeUpdateDTO);

        return ResponseEntity.ok(collegeGetMapper.convertDTOToModel(collegeGetDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {

        collegesService.deleteCollege(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
