package com.beehome.task_manager_back.models;

import com.beehome.task_manager_back.enums.TaskStatus;
import jakarta.persistence.*;
import java.util.Date;

import java.util.UUID;

@Entity
@Table(name = "BH_TB_TASK")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", nullable = false)
    private UserModel assignedTo;

    @PrePersist
    protected void onCreate() {
        this.createdOn = new Date(); // Define a data e hora atual
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public UserModel getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserModel assignedTo) {
        this.assignedTo = assignedTo;
    }
}
