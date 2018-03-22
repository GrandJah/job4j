package ru.job4j.webtest.model;

/**
 * Abstract model.
 */
public abstract class Model {
    /**
     * id model.
     */
    private int id;

    /**
     * @param id id model.
     */
    Model(int id) {
        this.id = id;
    }

    /**
     * @return id model
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id new id
     */
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
