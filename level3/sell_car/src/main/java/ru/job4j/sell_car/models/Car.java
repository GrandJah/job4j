package ru.job4j.sell_car.models;

import org.json.JSONObject;

/**
 * Car model class.
 */
public class Car {
    //        марка
    //        коробка
    //        год выпуска
    //        привод
    //        двигатель тип
    //        двигатель объем
    //        модель
    //        кузов тип
    //        пробег

    /**
     * id.
     */
    private int id;

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
     * images.
     */
    private Image[] images;

    /**
     * @return images
     */
    public Image[] getImages() {
        return images;
    }

    /**
     * @param images images
     */
    public void setImages(Image[] images) {
        this.images = images;
    }


    /**
     * model.
     */
    private String model;

    /**
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /** convert Car from json object.
     * @param car json object car
     * @return Car object
     */
    public static Car fromJSON(Object car) {
        JSONObject answer = new JSONObject(car);
        return null;
    }
}
