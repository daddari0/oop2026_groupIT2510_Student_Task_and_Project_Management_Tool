package Exceptions;

public class DeadlineInThePast extends RuntimeException {
    public DeadlineInThePast() {
        super("Deadline is set in the past.");
    }
}
