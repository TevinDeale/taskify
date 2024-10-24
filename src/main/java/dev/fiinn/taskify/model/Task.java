package dev.fiinn.taskify.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.fiinn.taskify.enums.Status;
import dev.fiinn.taskify.util.LocalDateDeserializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Status status;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dueDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdAt;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate updatedAt;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate completedAt;

    public Task(String title, String description, Status status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Task() {}

    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.status = builder.status;
        this.dueDate = builder.dueDate;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.completedAt = builder.completedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private Status status;
        private LocalDate dueDate;
        private LocalDate createdAt;
        private LocalDate updatedAt;
        private LocalDate completedAt;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setCreatedAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDate updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setCompletedAt(LocalDate completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder setDueDate(LocalDate dueDate){
            this.dueDate = dueDate;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
