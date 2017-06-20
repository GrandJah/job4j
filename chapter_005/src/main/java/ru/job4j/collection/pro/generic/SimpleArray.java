package ru.job4j.collection.pro.generic;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.06.2017
 *
 * @param <T> Тип элемента
 */
public class SimpleArray<T> {

    /**
     * Массив.
     */
    private T[]  array;
    /**
     * Размер массива.
     */
    private int size;

    /**
     * @param size размер массива
     */
    public SimpleArray(int size) {
        this.size = 0;
        this.array = (T[]) new Object[size];
    }

    /**
     * @param t Добавляемый элемент
     * @return добавленный элемент
     */
    public T add(T t) {
        this.array[size++] = t;
        return t;
    }

    /**
     * @param t новый элемент
     * @param index индекс обновляемого элемента
     * @return обновленный элемент
     */
    public T update(T t, int index) {
        this.array[index] = t;
        return t;
    }

    /**
     * @param index удаляемый элемент.
     */
    public void delete(int index) {
        System.arraycopy(this.array, index + 1, this.array, index, this.size - index);
        this.size--;
    }

    /**
     * @param index индекс
     * @return элемент массива
     */
    public T get(int index) {
        return this.array[index];
    }

    @Override
    public String toString() {
        String string = String.format("Array : {%s", this.size > 0 ? this.array[0] : "");
        for (int i = 1; i < this.size; i++) {
            string = String.format("%s, %s", string, this.array[i]);
        }
        string = String.format("%s}", string);

        return string;
    }
}
