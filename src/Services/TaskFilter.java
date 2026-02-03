package Services;

import Entities.Task;

@FunctionalInterface
public interface TaskFilter {
    boolean matches(Task task);
}
