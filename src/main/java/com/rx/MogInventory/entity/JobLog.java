package com.rx.MogInventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Table(name = "job_log")
@Entity
public class JobLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String job;

    @Column
    @NotNull
    private LocalDateTime date;

    public JobLog( String job, LocalDateTime date) {
        this.job = job;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
