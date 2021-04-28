package com.mgp.repository;

import com.mgp.repository.entities.CollegeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CollegesRepo extends CrudRepository<CollegeEntity, Long> {
}
