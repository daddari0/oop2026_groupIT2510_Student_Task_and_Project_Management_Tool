package Exceptions;

public class InvalidStatusTransition extends RuntimeException {
    public InvalidStatusTransition() {
        super("Invalid status transition.");
    }
}
