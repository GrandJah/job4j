package ru.job4j.compare;

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
        list.sort((first, second) -> first.hashCode() == second.hashCode() ? 0 : first.hashCode() < second.hashCode() ? -1 : 1);
        return list;
    }

    /** сортировка по длине имени.
     * @param list список
     * @return список
     */
    public List<User> sortLength(List<User> list) {
        list.sort((first, second) -> first.getName().length() == second.getName().length() ? 0
                : first.getName().length() < second.getName().length() ? -1 : 1);
        return list;
    }
}