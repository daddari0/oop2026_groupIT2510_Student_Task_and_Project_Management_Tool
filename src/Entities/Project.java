package Entities;

public class Project {
    private int id;
    private static int idgen;
    private String name;
    private String description;
    private String status;

    public Project(int id, String name, String description, String status) {
        this.id = idgen++;
        setName(name);
        setDescription(description);
        setStatus(status);
    }

    public int getId() {
        return id;
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
}
