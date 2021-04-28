package com.mgp.service;

import com.mgp.controller.model.Colleges.CollegeGetModel;
import com.mgp.mapper.Colleges.CollegeCreateMapper;
import com.mgp.mapper.Colleges.CollegeGetMapper;
import com.mgp.repository.CollegesRepo;
import com.mgp.repository.CoursesRepo;
import com.mgp.repository.entities.CollegeEntity;
import com.mgp.repository.entities.CourseEntity;
import com.mgp.service.dto.Colleges.CollegeCreateDTO;
import com.mgp.service.dto.Colleges.CollegeGetDTO;
import com.mgp.service.dto.Colleges.CollegeUpdateDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollegesService {

    private CollegesRepo collegesRepo;
    private CoursesRepo coursesRepo;
    private CollegeGetMapper collegeGetMapper;
    private CollegeCreateMapper collegeCreateMapper;

    public CollegesService(CollegesRepo collegesRepo, CoursesRepo coursesRepo) {

        this.collegesRepo = collegesRepo;
        this.coursesRepo = coursesRepo;
        this.collegeGetMapper = new CollegeGetMapper();
        this.collegeCreateMapper = new CollegeCreateMapper();
    }

    public List<CollegeGetDTO> getColleges() {
        List<CollegeEntity> collegeEntityList = (List<CollegeEntity>) collegesRepo.findAll();

        return collegeEntityList.stream()
                .map(collegeGetMapper::convertEntityToDTO)
                .collect(Collectors.toList());

    }


    public CollegeGetDTO addCollege(CollegeCreateDTO collegeCreateDTO) {

        List<CourseEntity> courseEntityList = new ArrayList<>();
        collegeCreateDTO.getCourses().forEach(courseId -> {
            CourseEntity courseEntity = coursesRepo.findById(courseId).orElseThrow(
                    () -> new EntityNotFoundException("Cannot find Course with id (" + courseId + ").")
            );
            courseEntityList.add(courseEntity);
        });

        CollegeEntity collegeEntity = collegeCreateMapper.convertDTOToEntity( collegeCreateDTO, courseEntityList);
        CollegeEntity collegeEntitySaved = collegesRepo.save(collegeEntity);

        return collegeGetMapper.convertEntityToDTO(collegeEntitySaved);

    }

    public void deleteCollege(Long id) {

        collegesRepo.deleteById(id);
    }

    public CollegeGetDTO updateCollege(Long id, CollegeUpdateDTO collegeUpdateDTO) {

        CollegeEntity collegeEntity = collegesRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find College with id (" + id + ").")
        );

        if(collegeUpdateDTO.getCourses() != null) {
            List<CourseEntity> courseEntityList = new ArrayList<>();

            collegeUpdateDTO.getCourses().forEach( courseId -> {
                CourseEntity courseEntity = coursesRepo.findById(courseId).orElseThrow(
                        () -> new EntityNotFoundException("Cannot find Course with id (" + courseId + ").")
                );
                courseEntityList.add(courseEntity);
            });

            collegeEntity.setCourses(courseEntityList);
        }

        if(collegeUpdateDTO.getName() != null)
            collegeEntity.setName(collegeUpdateDTO.getName());

        if(collegeUpdateDTO.getCity() != null)
            collegeEntity.setCity(collegeEntity.getCity());

        if(collegeUpdateDTO.getState() != null)
            collegeEntity.setState(collegeEntity.getState());

        if(collegeUpdateDTO.getShortName() != null)
            collegeEntity.setShortName(collegeEntity.getShortName());

        CollegeEntity collegeEntitySaved = collegesRepo.save(collegeEntity);

        return collegeGetMapper.convertEntityToDTO(collegeEntitySaved);

    }
}
