package ru.job4j.sell_car.models;

import org.json.JSONObject;

import java.util.List;

/**
 * Car model class.
 */
public class Car {
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
    private List<Image> images;

    /**
     * @return images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * @param images images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }


    /**
     * model.
     */
    private String description;

    /**
     * @return model
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param model model
     */
    public void setDescription(String model) {
        this.description = model;
    }

    /**
     * price.
     */
    private int price;

    /**
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /** convertArray Car from json object.
     * @param json json object car
     * @return Car object
     */
    public static Car fromJSON(JSONObject json) {
        Car car = new Car();
        car.setDescription(json.getString("model"));
        List<Image> images = Image.convert(json.getJSONArray("images"));
        for (Image image : images) {
            image.setCar(car);
        }
        car.setImages(images);
        return car;
    }

    /**
     * @return json object
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("price", this.price);
        json.put("description", this.description);
        json.put("images", Image.toJSON(getImages()));
        return json;
    }
}
