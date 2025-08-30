package com.hs.auth.project.domain;

import java.util.Objects;
import java.util.UUID;

public class ProjectId {
    private final String value;

    public ProjectId(String value) {
        this.value = validateValue(value);
    }

    public static ProjectId generate() {
        return new ProjectId(UUID.randomUUID().toString());
    }

    public static ProjectId from(String value) {
        return new ProjectId(value);
    }

    private String validateValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ProjectId cannot be null or empty");
        }
        return value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectId projectId = (ProjectId) o;
        return Objects.equals(value, projectId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ProjectId{" + "value='" + value + '\'' + '}';
    }
}