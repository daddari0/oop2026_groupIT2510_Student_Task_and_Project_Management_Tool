package Entities;

public class Comment {
    private int id;
    private static int idgen;
    private String content;
    private User author;
    private Task task;

    public Comment(int id, String content, User author, Task task) {
        this.id = idgen++;
        setContent(content);
        setAuthor(author);
        setTask(task);
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content is null.");
        }
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        if (author == null) {
            throw new IllegalArgumentException("Author is null.");
        }
        this.author = author;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task is null.");
        }
        this.task = task;
    }
}
