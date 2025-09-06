package com.hs.auth.project.application;

import com.hs.auth.common.event.SpringDomainEventPublisher;
import com.hs.auth.project.application.dto.RegisterProjectCommand;
import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.port.ProjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterProjectUseCase {
    private final ProjectRepository projectRepository;


    public RegisterProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;

    }

    public Project execute(RegisterProjectCommand command) {
        if (projectRepository.existsByName(command.getName())) {
            throw new IllegalArgumentException("Project with name '" + command.getName() + "' already exists");
        }

        Project project = new Project(command.getName(), command.getDescription(), command.getRedirectUri());
        Project savedProject = projectRepository.save(project);

        return savedProject;
    }
}