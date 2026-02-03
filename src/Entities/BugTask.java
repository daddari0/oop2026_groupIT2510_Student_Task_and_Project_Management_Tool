package Entities;

public class BugTask extends Task {
    private String severity; // LOW, MEDIUM, HIGH

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
}

