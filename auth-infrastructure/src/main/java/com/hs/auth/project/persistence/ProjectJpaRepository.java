package com.hs.auth.project.persistence;

import com.hs.auth.project.domain.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, String> {
    
    Optional<ProjectEntity> findByClientId(String clientId);
    
    List<ProjectEntity> findByStatus(ProjectStatus status);
    
    boolean existsByName(String name);
    
    boolean existsByClientId(String clientId);
    
    @Query("SELECT p FROM ProjectEntity p WHERE p.name = :name AND p.id != :excludeId")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("excludeId") String excludeId);
}