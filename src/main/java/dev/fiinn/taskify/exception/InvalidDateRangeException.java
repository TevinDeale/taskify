package dev.fiinn.taskify.exception;

public class InvalidDateRangeException extends Exception{

    public InvalidDateRangeException() {
        super("End date cannot be before start date");
    }
}
