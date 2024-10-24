package dev.fiinn.taskify.service;

import dev.fiinn.taskify.exception.*;
import dev.fiinn.taskify.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(String title, String description, String status, String dueDate) throws InvalidStatusException, InvalidDueDateException;
    void deleteTask(Long id) throws TaskNotFoundException;
    Task updateTask(String title, String description, String status, String dueDate, Long id) throws TaskNotFoundException, InvalidStatusException, InvalidDueDateException;
    List<Task> getAllTasks();
    Task getTaskById(Long id) throws TaskNotFoundException;
    List<Task> getTaskByStatus(String status) throws InvalidStatusException;
    List<Task> getTasksDueDateBetween(String startDate, String endDate) throws InvalidDateRangeException;
    List<Task> getTasksCreatedBetween(String startDate, String endDate) throws InvalidDateRangeException;
    List<Task> getTasksUpdatedBetween(String startDate, String endDate) throws InvalidDateRangeException;
    Task completeTask(Long id) throws TaskNotFoundException, GeneralTaskifyException;
    List<Task> searchTasksByTitleOrDescription(String searchTitle, String searchDescription);
    List<Task> getTaskCompletedBetween(String startDate, String endDate) throws InvalidDateRangeException;
    List<Task> getTaskDueToday();
    List<Task> getTasksCreatedToday();
    List<Task> getTasksCompletedToday();
    List<Task> getTasksUpdatedToday();
}
