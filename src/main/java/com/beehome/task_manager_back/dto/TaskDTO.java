package com.beehome.task_manager_back.dto;

import com.beehome.task_manager_back.enums.TaskStatus;

import java.util.Date;

public class TaskDTO {

    private String title;
    private String description;
    private TaskStatus status;
    private Date deadline;

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
