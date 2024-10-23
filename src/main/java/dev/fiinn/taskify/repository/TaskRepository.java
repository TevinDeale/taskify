package dev.fiinn.taskify.repository;

import dev.fiinn.taskify.enums.Status;
import dev.fiinn.taskify.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(Status status);
    List<Task> findAllByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
    List<Task> findAllByUpdatedAtBetween(LocalDate startDate, LocalDate endDate);
    List<Task> findAllByDueDateBetween(LocalDate startDate, LocalDate endDate);

}
