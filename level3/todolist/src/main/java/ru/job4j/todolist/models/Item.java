package ru.job4j.todolist.models;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Model Item.
 */
@Data
@Entity
@Table(name = "items")
public class Item {
    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * task name.
     */
    private String task;

    /**
     * description task.
     */
    private String description;

    /**
     * time created.
     */
    private Timestamp created = Timestamp.from(Instant.now());

    /**
     * item done.
     */
    private Boolean done = false;
}
