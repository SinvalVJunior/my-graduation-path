package com.mgp.repository;

import com.mgp.repository.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepo extends JpaRepository<StudentEntity, Long> {
}
