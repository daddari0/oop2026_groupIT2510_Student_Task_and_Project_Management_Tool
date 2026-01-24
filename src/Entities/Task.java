package Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private long id;
    private static long idgen;
    private String title;
    private String description;
    private String status;
    private User assignee;
    private Project project;
    private Long assigneeId;
    private Long projectId;
    private LocalDate deadline;
    private LocalDateTime createdAt;

    public Task() {
    }

    public Task(long id, String title, String description,
                String status, User assignee, Project project) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setStatus(status);
        setAssignee(assignee);
        setProject(project);
    }

    public Task(String title, String description,
                String status, User assignee, Project project) {
        this.id = idgen++;
        setTitle(title);
        setDescription(description);
        setStatus(status);
        setAssignee(assignee);
        setProject(project);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is null.");
        }
        this.title = title;
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


    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
        this.assigneeId = assignee != null ? assignee.getId() : null;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        this.projectId = project != null ? project.getId() : null;
    }

    public Long getProjectId() {
        if (project != null) {
            return project.getId();
        }
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
        if (this.project == null) {
            this.project = new Project();
        }
        this.project.setId(projectId);
    }

    public Long getAssigneeId() {
        if (assignee != null) {
            return assignee.getId();
        }
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
        if (assigneeId == null) {
            this.assignee = null;
        } else {
            if (this.assignee == null) {
                this.assignee = new User();
            }
            this.assignee.setId(assigneeId);
        }
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
}
