package com.example.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by  Yefimov Yevhen on 10.11.2020
 * Base POJO class with property ID
 */
@Document(indexName = "numbers")
public class RandomNumber {
    @Id
    private String id;

    @Field(type = FieldType.Long)
    private long number;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Boolean)
    private boolean found;

    @Field(type = FieldType.Long)
    private long latency;


    public RandomNumber() {
    }

    public RandomNumber(String id, long number, String text, boolean found, long latency) {
        this.id = id;
        this.number = number;
        this.text = text;
        this.found = found;
        this.latency = latency;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "RandomNumber{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", text='" + text + '\'' +
                ", found=" + found +
                ", latency=" + latency +
                '}';
    }
}
