package ru.job4j.nonblockcache;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.12.2017
 * @param <K> key
 * @param <T> type extend Model
 */
@ThreadSafe
public class NonBlockCache<K, T extends NonBlockCache.Model> {
    /** Неблокирующая коллекция. */
    private ConcurrentHashMap<K, Model> map;

    /** Default. */
    public NonBlockCache() {
        this(100);
    }

    /** @param initCapacity начальная емкость .*/
    public NonBlockCache(int initCapacity) {
        this.map = new ConcurrentHashMap<>(initCapacity);
    }

    /** Добавление модели.
     * @param key ключ
     * @param model модель
     * @return true если добавлено, false в противном случае
     */
    public boolean add(K key, T model) {
        return this.map.putIfAbsent(key, model) == null;
    }

    /** Удаление модели.
     * @param key ключ
     * @param model модель
     * @return true если удалено, false в противном случае
     */
    public boolean delete(K key, T model) {
        return this.map.remove(key, model);
    }

    /** Обновление модели.
     * @param key ключ
     * @param model модель
     * @throws OptimisticException если обнаружено несинхронизированное обновление.
     */
    public void update(K key, Model model) throws OptimisticException {
        if (this.map.computeIfPresent(key, (k, oldModel) -> {
            Model ret;
            if (oldModel.version == model.version) {
                model.version++;
                ret = model;
            } else {
                ret = null;
            }
            return ret;
        }) == null) {
            throw new OptimisticException();
        }
    }

    /** Модель. */
    abstract class Model {
        /** Версия модели. */
        private int version = 0;
    }


}

/** Исключение обнаруженой ошибки синхронизации. */
class OptimisticException extends Exception {

}
