package com.example.backendquizmentor.repos;

import com.example.backendquizmentor.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
    @Query("SELECT u FROM CustomUser u where u.login =:login")
    CustomUser findByLogin(@Param("login") String login);

    boolean existsByLogin(String username);

}
