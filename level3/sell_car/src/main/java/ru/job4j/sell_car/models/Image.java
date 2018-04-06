package ru.job4j.sell_car.models;

import org.json.JSONArray;
import ru.job4j.sell_car.Hibernate;

import java.util.ArrayList;
import java.util.List;

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
    private byte[] image;

    /**
     * Car.
     */
    private Car car;

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
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * @return car
     */
    public Car getCar() {
        return car;
    }

    /**
     * @param car car
     */
    public void setCar(Car car) {
        this.car = car;
    }


    /**
     * @param jsonImages jsonImage array
     * @return Image array
     */
    public static List<Image> convert(JSONArray jsonImages) {
        List<Image> images = new ArrayList<>();
        for (Object path : jsonImages) {
            images.add(Image.valueOf((String) path));
        }
        return images;
    }

    /**
     * @param images images
     * @return json array
     */
    public static JSONArray toJSON(List<Image> images) {
        JSONArray json = new JSONArray();
        for (Image image : images) {
            json.put(image.path);
        }
        return json;
    }

    //TODO Реализовать метод возврата уже созданного изображения по пути к файлу с проверкой переиспользования в бд.
    /** Extract Image.
     * @param path path
     * @return Image
     */
    private static Image valueOf(String path) {
        Image image = new Image();
        image.setPath(path);
        image.setImage(new byte[]{});
        try (Hibernate hibernate = new Hibernate()) {
            hibernate.save(image);
        }
        return image;
    }
}
