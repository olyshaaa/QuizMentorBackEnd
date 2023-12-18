package com.example.backendquizmentor.repos;

import com.example.backendquizmentor.model.CustomModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<CustomModule, Long> {
    CustomModule save(CustomModule module);
}
