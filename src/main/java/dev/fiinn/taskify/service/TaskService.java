package dev.fiinn.taskify.service;

import dev.fiinn.taskify.enums.Status;
import dev.fiinn.taskify.exception.*;
import dev.fiinn.taskify.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task createTask(String title, String description, String status, String dueDate) throws InvalidStatusException, InvalidDueDateException;
    void deleteTask(Long id) throws TaskNotFoundException;
    Task updateTask(String title, String description, String status, String dueDate, Long id) throws TaskNotFoundException, InvalidStatusException, InvalidDueDateException;
    List<Task> getAllTasks();
    Task getTaskById(Long id) throws TaskNotFoundException;
    List<Task> getTaskByStatus(Status status);
    List<Task> getTasksDueDateBetween(LocalDate startDate, LocalDate endDate) throws InvalidDateRangeException;
    List<Task> getTasksCreatedBetween(LocalDate startDate, LocalDate endDate) throws InvalidDateRangeException;
    List<Task> getTasksUpdatedBetween(LocalDate startDate, LocalDate endDate) throws InvalidDateRangeException;
    Task completeTask(Long id) throws TaskNotFoundException, GeneralTaskifyException;

}
