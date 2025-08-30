package com.hs.auth.project.application;

import com.hs.auth.common.event.SpringDomainEventPublisher;
import com.hs.auth.project.application.dto.RegisterProjectCommand;
import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.ProjectRepository;
import com.hs.auth.project.domain.event.ProjectRegisteredEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterProjectUseCase {
    private final ProjectRepository projectRepository;
    private final SpringDomainEventPublisher springDomainEventPublisher;

    public RegisterProjectUseCase(ProjectRepository projectRepository, SpringDomainEventPublisher springDomainEventPublisher) {
        this.projectRepository = projectRepository;
        this.springDomainEventPublisher = springDomainEventPublisher;
    }

    public Project execute(RegisterProjectCommand command) {
        if (projectRepository.existsByName(command.getName())) {
            throw new IllegalArgumentException("Project with name '" + command.getName() + "' already exists");
        }

        Project project = new Project(command.getName(), command.getDescription(), command.getRedirectUri());
        Project savedProject = projectRepository.save(project);

        springDomainEventPublisher.publish(new ProjectRegisteredEvent(savedProject.getId(), command.getName(), command.getDescription(), command.getRedirectUri()));
        return savedProject;
    }
}