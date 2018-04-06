package ru.job4j.sell_car.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 *  Announcement model class.
 */
public class Announcement {
    /**
     * id.
     */
    private int id;

    /**
     * user.
     */
    private User user;

    /**
     * car.
     */
    private Car car;

    /**
     * sell.
     */
    private boolean sell;

    /** constructor new announcement.
     * @param user user
     * @param car car
     */
    public Announcement(User user, Car car) {
        this.user = user;
        this.car = car;
        this.sell = false;
    }

    /**
     * default.
     */
    public Announcement() {
    }

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
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;
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
     * @return sell
     */
    public boolean isSell() {
        return sell;
    }

    /**
     * @param sell sell
     */
    public void setSell(boolean sell) {
        this.sell = sell;
    }

    /**
     * @return json object
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("car", this.car.toJSON());
        json.put("user", this.user.getLogin());
        json.put("sell", this.sell);
        return json;
    }

    /**
     * @param announcements announcements
     * @return json array
     */
    public static JSONArray toJSON(List<Announcement> announcements) {
        JSONArray json = new JSONArray();
        for (Announcement announcement : announcements) {
            json.put(announcement.toJSON());
        }
        return json;
    }

}
