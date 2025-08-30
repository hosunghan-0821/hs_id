package com.hs.auth.project.persistence;

import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.ProjectId;
import com.hs.auth.project.domain.ProjectRepository;
import com.hs.auth.project.domain.ProjectStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hs.auth.project.persistence.QProjectEntity.projectEntity;


@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    
    private final ProjectJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(ProjectJpaRepository jpaRepository, JPAQueryFactory queryFactory) {
        this.jpaRepository = jpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public Project save(Project project) {
        ProjectEntity entity = ProjectEntity.from(project);
        ProjectEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Project> findById(ProjectId id) {
        return jpaRepository.findById(id.getValue())
                .map(ProjectEntity::toDomain);
    }

    @Override
    public Optional<Project> findByClientId(String clientId) {
        return jpaRepository.findByClientId(clientId)
                .map(ProjectEntity::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(ProjectEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> findByStatus(ProjectStatus status) {
        return queryFactory
                .selectFrom(projectEntity)
                .where(projectEntity.status.eq(status))
                .orderBy(projectEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(ProjectEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByClientId(String clientId) {
        return jpaRepository.existsByClientId(clientId);
    }

    @Override
    public void deleteById(ProjectId id) {
        jpaRepository.deleteById(id.getValue());
    }

    public List<Project> findByNameContaining(String nameKeyword) {
        return queryFactory
                .selectFrom(projectEntity)
                .where(projectEntity.name.containsIgnoreCase(nameKeyword))
                .orderBy(projectEntity.name.asc())
                .fetch()
                .stream()
                .map(ProjectEntity::toDomain)
                .collect(Collectors.toList());
    }

    public List<Project> findActiveProjectsCreatedAfter(java.time.LocalDateTime date) {
        return queryFactory
                .selectFrom(projectEntity)
                .where(
                    projectEntity.status.eq(ProjectStatus.ACTIVE)
                    .and(projectEntity.createdAt.after(date))
                )
                .orderBy(projectEntity.createdAt.desc())
                .fetch()
                .stream()
                .map(ProjectEntity::toDomain)
                .collect(Collectors.toList());
    }
}