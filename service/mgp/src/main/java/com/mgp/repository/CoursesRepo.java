package com.mgp.repository;

import com.mgp.repository.entities.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepo extends CrudRepository<CourseEntity, Long> {
}
