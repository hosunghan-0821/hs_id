package com.hs.auth.project.application;

import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.ProjectId;
import com.hs.auth.project.domain.ProjectRepository;
import com.hs.auth.project.domain.ProjectStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectQueryService {
    private final ProjectRepository projectRepository;

    public ProjectQueryService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> findById(ProjectId projectId) {
        return projectRepository.findById(projectId);
    }

    public Optional<Project> findByClientId(String clientId) {
        return projectRepository.findByClientId(clientId);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> findActiveProjects() {
        return projectRepository.findByStatus(ProjectStatus.ACTIVE);
    }

    public boolean existsByName(String name) {
        return projectRepository.existsByName(name);
    }

    public boolean existsByClientId(String clientId) {
        return projectRepository.existsByClientId(clientId);
    }
}