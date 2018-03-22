package ru.job4j.collection.pro.generic;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 28.06.2017
 *
 * @param <T> Тип контейнера
 */
public abstract class StoreBase<T extends Base> implements Store<T> {
    /**
     * RoleStore.
     */
    private SimpleArray<T> array = new SimpleArray<>(100);

    @Override
    public T add(T base) {
        return this.array.add(base);
    }

    @Override
    public T update(T base, T update) {
        return this.array.update(base, update);
    }

    @Override
    public void remove(T base) {
        this.array.delete(this.array.getIndex(base));
    }
}
