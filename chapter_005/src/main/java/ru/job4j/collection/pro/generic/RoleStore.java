package ru.job4j.collection.pro.generic;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.06.2017
 */
public class RoleStore implements Store<Role> {
    /**
     * RoleStore.
     */
    private SimpleArray<Role> array = new SimpleArray<>(100);

    @Override
    public Role add(Role role) {
        return this.array.add(role);
    }

    @Override
    public Role update(Role role, Role updateRole) {
        return this.array.update(role, updateRole);
    }

    @Override
    public void remove(Role role) {
        this.array.delete(this.array.getIndex(role));
    }
}


