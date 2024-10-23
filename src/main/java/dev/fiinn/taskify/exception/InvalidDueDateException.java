package dev.fiinn.taskify.exception;

public class InvalidDueDateException extends Exception{

    public InvalidDueDateException() {
        super("Due date cannot be in the past.");
    }
}
