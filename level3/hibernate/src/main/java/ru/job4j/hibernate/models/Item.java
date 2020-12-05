package ru.job4j.hibernate.models;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.hibernate.models.dao.ItemDao;

import java.util.List;

/**
 * Model Item.
 */
@Data
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
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
    
    /**
     * @return All field's names
     */
    public static String[] getFields() {
        return new String[] {"id", "task", "description", "created", "done"};
    }

    /**
     * @return all Items.
     */
    public static List<Item> getAll() {
        return ItemDao.getAll();
    }

    /** Create new Item.
     * @param task task
     * @param  description description
     * @return new Item or null.
     */
    public static Item create(String task, String description) {
        Item item = new Item();
        item.setTask(task);
        item.setDescription(description);
        return ItemDao.create(item);
    }
}
