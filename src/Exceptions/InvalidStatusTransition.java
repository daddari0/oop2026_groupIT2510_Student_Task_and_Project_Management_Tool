package Exceptions;

public class InvalidStatusTransition extends RuntimeException {
    public InvalidStatusTransition() {
        super("invalid status transition");
    }
}
