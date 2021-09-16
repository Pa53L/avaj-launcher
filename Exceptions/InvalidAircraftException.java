package Exceptions;

public class InvalidAircraftException extends Exception {
    private static final String MESSAGE = "Invalid format of string, containing aircraft.";
    public InvalidAircraftException() {
        super(MESSAGE);
    }
}
