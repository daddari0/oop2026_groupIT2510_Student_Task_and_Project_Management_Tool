package Exceptions;

public class TaskWithoutProject extends RuntimeException {
    public TaskWithoutProject() {
        super("Task is not associated with any project");
    }
}
