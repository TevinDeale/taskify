package dev.fiinn.taskify.enums;

public enum Status {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    NOT_FOUND("Not Found");

    private final String displayStatus;

    Status(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    @Override
    public String toString() {
        return this.displayStatus;
    }

    public static Status getStatus(String status) {

        return switch (status.toUpperCase()) {
            case "PENDING" -> PENDING;
            case "IN_PROGRESS" -> IN_PROGRESS;
            case "COMPLETED" -> COMPLETED;
            default -> NOT_FOUND;
        };
    }
}
