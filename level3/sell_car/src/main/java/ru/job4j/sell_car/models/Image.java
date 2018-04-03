package ru.job4j.sell_car.models;

import java.sql.Blob;

/**
 * Image model class.
 */
public class Image {

    /**
     * id.
     */
    private int id;

    /**
     * path.
     */
    private String path;

    /**
     * image.
     */
    private Blob image;

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return image
     */
    public Blob getImage() {
        return image;
    }

    /**
     * @param image image
     */
    public void setImage(Blob image) {
        this.image = image;
    }
}
