package dev.fiinn.taskify.service;

import dev.fiinn.taskify.enums.Status;
import dev.fiinn.taskify.exception.*;
import dev.fiinn.taskify.model.Task;
import dev.fiinn.taskify.repository.TaskRepository;
import dev.fiinn.taskify.util.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public List<Task> getTaskByStatus(String status) throws InvalidStatusException {

        Status checkStatus = Status.getStatus(status);

        if (checkStatus.equals(Status.NOT_FOUND)) {
            throw new InvalidStatusException("Invalid status in path, please use on of the following:\nPENDING\nIN_PROGRESS\nCOMPLETED");
        }

        return taskRepository.findAllByStatus(checkStatus);
    }

    @Override
    public List<Task> getTasksDueDateBetween(String startDate, String endDate) throws InvalidDateRangeException {
        LocalDate startLocal = DateUtil.parseString(startDate);
        LocalDate endLocal = DateUtil.parseString(endDate);

        if (!DateUtil.validDateRange(startLocal, endLocal)) {
            throw new InvalidDateRangeException();
        }
        return taskRepository.findAllByDueDateBetween(startLocal, endLocal);
    }

    @Override
    public List<Task> getTasksCreatedBetween(String startDate, String endDate) throws InvalidDateRangeException {

        LocalDate startLocal = DateUtil.parseString(startDate);
        LocalDate endLocal = DateUtil.parseString(endDate);

        if (!DateUtil.validDateRange(startLocal, endLocal)) {
            throw new InvalidDateRangeException();
        }

        return taskRepository.findAllByCreatedAtBetween(startLocal, endLocal);
    }

    @Override
    public List<Task> getTaskCompletedBetween(String startDate, String endDate) throws InvalidDateRangeException {

        LocalDate startLocal = DateUtil.parseString(startDate);
        LocalDate endLocal = DateUtil.parseString(endDate);

        if (!DateUtil.validDateRange(startLocal, endLocal)) {
            throw new InvalidDateRangeException();
        }

        return taskRepository.findAllByCompletedAtBetween(startLocal, endLocal);
    }

    @Override
    public List<Task> getTasksUpdatedBetween(String startDate, String endDate) throws InvalidDateRangeException {

        LocalDate startLocal = DateUtil.parseString(startDate);
        LocalDate endLocal = DateUtil.parseString(endDate);

        if (!DateUtil.validDateRange(startLocal, endLocal)) {
            throw new InvalidDateRangeException();
        }

        return taskRepository.findAllByUpdatedAtBetween(startLocal, endLocal);
    }

    @Override
    public List<Task> getTaskDueToday() {
        LocalDate now = DateUtil.now();
        return taskRepository.findAllByDueDateBetween(now, now);
    }

    @Override
    public List<Task> getTasksCreatedToday() {
        LocalDate now = DateUtil.now();
        return taskRepository.findAllByCreatedAtBetween(now, now);
    }

    @Override
    public List<Task> getTasksCompletedToday() {
        LocalDate now = DateUtil.now();
        return taskRepository.findAllByCompletedAtBetween(now, now);
    }

    @Override
    public List<Task> getTasksUpdatedToday() {
        LocalDate now = DateUtil.now();
        return taskRepository.findAllByUpdatedAtBetween(now, now);
    }

    @Override
    public List<Task> searchTasksByTitleOrDescription(String searchTitle, String searchDescription) {
        if (searchTitle.trim().isBlank() || searchDescription.trim().isBlank()) {
            throw new IllegalArgumentException("Search term cannot be blank.");
        }

        return taskRepository.findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchTitle, searchDescription);
    }

    @Transactional
    @Override
    public Task createTask(String title, String description, String status, String dueDate)
            throws InvalidStatusException, InvalidDueDateException {

        nullCheck(title, description, dueDate, status);

        LocalDate dueDate1 = DateUtil.parseString(dueDate);

        Status checkStatus = Status.getStatus(status);

        if (checkStatus.equals(Status.NOT_FOUND) || checkStatus.equals(Status.COMPLETED)) {
            throw new InvalidStatusException("Invalid status, please use on of the following:\nPENDING\nIN_PROGRESS");
        }

        if (dueDate1.isBefore(DateUtil.now())) {
            throw new InvalidDueDateException();
        }

        Task newTask = new Task.Builder()
                .setName(title)
                .setDescription(description)
                .setStatus(checkStatus)
                .setDueDate(dueDate1)
                .setCreatedAt(DateUtil.now())
                .setUpdatedAt(DateUtil.now())
                .build();

        return taskRepository.save(newTask);
    }

    @Transactional
    @Override
    public void deleteTask(Long id) throws TaskNotFoundException {
        taskRepository.delete(
                taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id))
        );
    }

    @Transactional
    @Override
    public Task updateTask(String title, String description, String status, String dueDate, Long id)
            throws TaskNotFoundException, InvalidStatusException, InvalidDueDateException {

        nullCheck(title, description, dueDate, status);

        Task foundTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        LocalDate dueDate1 = DateUtil.parseString(dueDate);

        Status checkStatus = Status.getStatus(status);

        if (checkStatus.equals(Status.NOT_FOUND)) {
            throw new InvalidStatusException("Invalid status, please use on of the following:\nPENDING\nIN_PROGRESS");
        }

        if (checkStatus.equals(Status.COMPLETED)) {
            throw new InvalidStatusException("Cannot complete status with the update endpoint. Please use the following endpoint to complete tasks: /api/tasks/complete/{id}");
        }

        if (dueDate1.isBefore(DateUtil.now())) {
            throw new InvalidDueDateException();
        }

        foundTask.setTitle(title);
        foundTask.setDescription(description);
        foundTask.setStatus(checkStatus);
        foundTask.setDueDate(dueDate1);
        foundTask.setUpdatedAt(DateUtil.now());

        return taskRepository.save(foundTask);
    }

    @Transactional
    @Override
    public Task completeTask(Long id) throws TaskNotFoundException, GeneralTaskifyException {
        Task foundTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (foundTask.getStatus().equals(Status.COMPLETED)) {
            throw new GeneralTaskifyException("Task with ID: " + id + " is already marked completed.");
        }

        foundTask.setStatus(Status.COMPLETED);
        foundTask.setCompletedAt(DateUtil.now());

        return taskRepository.save(foundTask);
    }

    private void nullCheck(String title, String description, String dueDate, String status) {

        List<String> strings = new ArrayList<>();
        strings.add(title.trim());
        strings.add(description.trim());
        strings.add(dueDate.trim());
        strings.add(status.trim());

        strings.forEach(s -> {
            if (s.isBlank()) {
                throw new IllegalArgumentException(s.getClass().getName() + " Cannot be blank.");
            }
        });
    }
}