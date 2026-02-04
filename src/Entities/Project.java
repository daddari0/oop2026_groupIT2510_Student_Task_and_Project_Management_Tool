package Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Project {
    private long id;
    private static long idgen;
    private String name;
    private String description;
    private String status;

    private Long ownerId;
    private LocalDate deadline;
    private LocalDateTime createdAt;

    public Project() {
    }

    public Project(long id, String name, String description, String status) {
        this.id = id;
        setName(name);
        setDescription(description);
        setStatus(status);
    }

    public Project(String name, String description, String status) {
        this.id = idgen++;
        setName(name);
        setDescription(description);
        setStatus(status);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is null.");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description is null.");
        }
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is null.");
        }
        this.status = status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    private Project(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.status = builder.status;
        this.ownerId = builder.ownerId;
        this.deadline = builder.deadline;
        this.createdAt = builder.createdAt;
    }

    public static class Builder {
        private String name;
        private String description;
        private String status;
        private Long ownerId;
        private LocalDate deadline;
        private LocalDateTime createdAt;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder ownerId(Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder deadline(LocalDate deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Project build() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Project name is required");
            }
            if (status == null || status.isBlank()) {
                status = "ACTIVE";
            }
            return new Project(this);
        }
    }
}