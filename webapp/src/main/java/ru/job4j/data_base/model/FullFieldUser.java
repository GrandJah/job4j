package ru.job4j.data_base.model;

import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UseLocation;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class FullFieldUser implements JSONConvert {
    /**
     * User base model.
     */
    private User user;

    /** constructor extend Model.
     * @param user user.
     */
    public FullFieldUser(User user) {
        this.user = user;
    }

    @Override
    public String toJSON() {
        StringBuilder user = new StringBuilder(this.user.toJSON());
        user.insert(user.length() - 1, String.format(",\"role\":\"%s\"",
                new RoleStore().getUserRole(this.user.getLogin()).name()));
        user.insert(user.length() - 1, String.format(",\"location\":%s",
                new UseLocation().getUserLocation(this.user.getLogin()).toJSON()));
        return user.toString();
    }
}
