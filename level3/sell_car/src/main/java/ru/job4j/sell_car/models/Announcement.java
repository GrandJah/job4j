package ru.job4j.sell_car.models;

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
}
