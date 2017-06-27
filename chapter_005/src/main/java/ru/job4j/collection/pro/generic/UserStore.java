package ru.job4j.collection.pro.generic;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.06.2017
 */
public class UserStore implements Store<User> {
    /**
     * UserStore.
     */
    private SimpleArray<User> array = new SimpleArray<>(100);

    @Override
    public User add(User user) {
        return this.array.add(user);
    }

    @Override
    public User update(User user, User updateUser) {
        return this.array.update(user, updateUser);
    }

    @Override
    public void remove(User user) {
        this.array.delete(this.array.getIndex(user));
    }
}
