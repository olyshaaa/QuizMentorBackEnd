package com.example.backendquizmentor.repos;

import com.example.backendquizmentor.model.CustomModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<CustomModule, Long> {
    CustomModule save(CustomModule module);
    @Query("SELECT cm FROM CustomModule cm WHERE cm.authorUsername = :authorUsername")
    List<CustomModule> findByAuthorUsername(@Param("authorUsername") String authorUsername);
    
    List<CustomModule> findAll();

    @Query("SELECT cm FROM CustomModule cm WHERE cm.moduleName = :request")
    List<CustomModule> findByRequest(@Param("request") String request);
}
