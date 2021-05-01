package com.mgp.repository;

import com.mgp.repository.entities.SemesterEntity;
import org.springframework.data.repository.CrudRepository;

public interface SemestersRepo extends CrudRepository<SemesterEntity, Long> {
}
