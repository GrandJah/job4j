package ru.job4j.hibernate.models;

import ru.job4j.hibernate.models.dao.ItemDao;

import java.util.List;

/**
 * Model Item.
 */
public class Item {

    /**
     * id.
     */
    private int id;

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
    private long created;

    /**
     * item done.
     */
    private boolean done;

    /** getter.
     * @return id
     */
    public int getId() {
        return id;
    }

    /** setter.
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /** getter.
     * @return task
     */
    public String getTask() {
        return task;
    }

    /** setter.
     * @param task task
     */
    public void setTask(String task) {
        this.task = task;
    }

    /** getter.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /** setter.
     * @param desc description
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    /** getter.
     * @return created
     */
    public long getCreated() {
        return created;
    }

    /** setter.
     * @param created created
     */
    public void setCreated(long created) {
        this.created = created;
    }

    /** getter.
     * @return done
     */
    public boolean isDone() {
        return done;
    }

    /** setter.
     * @param done done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

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
        item.setCreated(System.currentTimeMillis());
        item.setDone(false);
        return ItemDao.create(item);
    }
}
