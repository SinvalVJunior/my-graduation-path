package com.mgp.repository;

import com.mgp.repository.entities.ClassEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepo extends CrudRepository<ClassEntity, Long> {
}
