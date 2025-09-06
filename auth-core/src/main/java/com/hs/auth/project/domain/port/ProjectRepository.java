package com.hs.auth.project.domain.port;

import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.ProjectId;
import com.hs.auth.project.domain.ProjectStatus;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);
    
    Optional<Project> findById(ProjectId id);
    
    Optional<Project> findByClientId(String clientId);
    
    List<Project> findAll();
    
    List<Project> findByStatus(ProjectStatus status);
    
    boolean existsByName(String name);
    
    boolean existsByClientId(String clientId);
    
    void deleteById(ProjectId id);
}