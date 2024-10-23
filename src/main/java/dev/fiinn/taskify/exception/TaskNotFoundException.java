package dev.fiinn.taskify.exception;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
