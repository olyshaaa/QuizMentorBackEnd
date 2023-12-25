package com.example.backendquizmentor.repos;

import com.example.backendquizmentor.model.CustomModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<CustomModule, Long> {
    CustomModule save(CustomModule module);

    List<CustomModule> findByAuthorUsername(String authorUsername);
}
