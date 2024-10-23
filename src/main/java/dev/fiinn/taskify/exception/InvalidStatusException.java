package dev.fiinn.taskify.exception;

import dev.fiinn.taskify.enums.Status;

public class InvalidStatusException extends Exception{
    public InvalidStatusException(String message) {
        super(message);
    }
}
