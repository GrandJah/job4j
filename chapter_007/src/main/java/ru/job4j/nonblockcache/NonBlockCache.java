package ru.job4j.nonblockcache;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.12.2017
 */
@ThreadSafe
public class NonBlockCache {
    /**
     * Неблокирующая коллекция.
     */
    private ConcurrentHashMap<Integer, Model> map;

    /**
     * Default.
     */
    public NonBlockCache() {
        this(100);
    }

    /**
     * @param initCapacity начальная емкость .
     */
    public NonBlockCache(int initCapacity) {
        this.map = new ConcurrentHashMap<>(initCapacity);
    }

    /**
     * Добавление модели.
     *
     * @param key   ключ
     * @param model модель
     * @return true если добавлено, false в противном случае
     */
    public boolean add(Integer key, Model model) {
        return this.map.putIfAbsent(key, model) == null;
    }

    /**
     * Удаление модели.
     *
     * @param key   ключ
     * @param model модель
     * @return true если удалено, false в противном случае
     */
    public boolean delete(Integer key, Model model) {
        return this.map.remove(key, model);
    }

    /**
     * Обновление модели.
     *
     * @param key   ключ
     * @param model модель
     * @throws OptimisticException если обнаружено несинхронизированное обновление.
     */
    public void update(Integer key, Model model) throws OptimisticException {
        if (this.map.computeIfPresent(key, (k, oldModel) -> {
            Model ret;
            if (oldModel.version == model.version) {
                model.version++;
                ret = model;
            } else {
                ret = oldModel;
            }
            return ret;
        }) != model) {
            throw new OptimisticException();
        }
    }

    /**
     * Взять модель.
     *
     * @param key ключ.
     * @return модель.
     */
    public Model get(Integer key) {
        return this.map.get(key).clone();
    }

    /**
     * Абстрактный класс модели.
     */
    abstract static class Model implements Cloneable {
        /** Версия модели. */
        private int version = 0;

        @Override
        protected Model clone() {
            Model model = null;
            try {
                model = (Model) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return model;
        }
    }
}