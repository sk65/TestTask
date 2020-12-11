package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


/**
 * Created by  Yefimov Yevhen on 10.11.2020
 * Base POJO class with property ID
 */
@Entity
@Table(name = "number")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomNumber {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private long number;

    @Column(name = "text")
    private String text;

    @Column(name = "found")
    private boolean found;

    @Column(name = "latency")
    private long latency;

    public RandomNumber() {
    }

    public RandomNumber(long number, String text, boolean found, long latency) {
        this.number = number;
        this.text = text;
        this.found = found;
        this.latency = latency;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public long getLatency() {
        return latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }
}
