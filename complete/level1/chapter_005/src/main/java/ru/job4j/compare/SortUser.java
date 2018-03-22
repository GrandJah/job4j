package ru.job4j.compare;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class SortUser {
    /**
     * @param list список
     * @return отсортированное множество
     */
    public Set<User> sort(List<User> list) {
        Set<User> set = new TreeSet<>();
        set.addAll(list);
        return set;
    }

    /** сортировка по hash-коду.
     * @param list список
     * @return список
     */
    public List<User> sortHash(List<User> list) {
        list.sort(Comparator.comparingInt(User::hashCode));
        return list;
    }

    /** сортировка по длине имени.
     * @param list список
     * @return список
     */
    public List<User> sortLength(List<User> list) {
        list.sort(Comparator.comparingInt(user -> user.getName().length()));
        return list;
    }
}
