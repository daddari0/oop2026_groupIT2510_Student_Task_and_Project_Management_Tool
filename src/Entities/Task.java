package Entities;

public class Task {
    private int id;
    private static int idgen;
    private String title;
    private String description;
    private String status;
    private User assignee;
    private Project project;

    public Task(int id, String title, String description, String status, User assignee, Project project) {
        this.id = idgen++;
        setTitle(title);
        setDescription(description);
        setStatus(status);
        setAssignee(assignee);
        setProject(project);
    }

    public int getId() {
        return id;
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
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
}
