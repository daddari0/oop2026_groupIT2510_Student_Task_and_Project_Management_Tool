package Services;

import Entities.*;

import java.time.LocalDate;

public class TaskFactory {

    public static Task createTask(TaskType type,
                                  Long projectId,
                                  Long assigneeId,
                                  String title,
                                  String description,
                                  String status,
                                  LocalDate deadline) {

        Task task;
        switch (type) {
            case BUG -> {
                BugTask bug = new BugTask();
                bug.setSeverity("MEDIUM");
                task = bug;
            }
            case FEATURE -> {
                FeatureTask feature = new FeatureTask();
                feature.setFeatureArea("GENERAL");
                task = feature;
            }
            case RESEARCH -> {
                ResearchTask research = new ResearchTask();
                research.setResearchTopic("unspecified");
                task = research;
            }
            default -> throw new IllegalArgumentException("Unknown task type: " + type);
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setDeadline(deadline);
        task.setProjectId(projectId);
        task.setAssigneeId(assigneeId);

        return task;
    }
}
