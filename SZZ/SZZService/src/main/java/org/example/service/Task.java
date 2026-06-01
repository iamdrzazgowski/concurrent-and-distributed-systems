package org.example.service;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private int id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate deadline;

    public Task() {
    }

    public Task(int id,
                String title,
                String description,
                Priority priority,
                LocalDate deadline) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return id + " | " + title + " | " +
                priority + " | " + deadline;
    }
}
