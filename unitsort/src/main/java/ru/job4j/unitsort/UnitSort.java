package ru.job4j.unitsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.06.2017
 */
public class UnitSort {

    /**
     * Карта подразделений.
     */
    private TreeMap<String, TreeMap> map = new TreeMap<>();

    /** Сортировка по возрастанию.
     * @param units список подразделений
     * @return сортированный список
     */
    public String[] sortAscent(String[] units) {
        return sortAction(true, units);
    }

    /** Сортировка по убыванию.
     * @param units список подразделений
     * @return сортированный список
     */
    public String[] sortDescent(String[] units) {
        return sortAction(false, units);
    }

    /** Сортировка по направлению.
     * @param direction напрвление сортировки
     * @param units список подразделений
     * @return сортированный список
     */
    private String[] sortAction(boolean direction, String[] units) {
        for (String unit : units) {
            add(unit);
        }
        List<String> list = new ArrayList<>();
        fromMap("", this.map, list, direction);
        this.map = new TreeMap<>();
        return list.toArray(new String[list.size()]);
    }


    /** Добавление подразделения в карту подразделений.
     * @param unit подразделение
     */
    private void add(String unit) {
        inToMap(unit.split("\\\\"), this.map);
    }

    /** Добавление подразделения в карту подразделений.
     * рекурсивный поиск места для вставки
     * @param unit добавляемое подразделение
     * @param paramMap карта текущего подразделения
     */
    private void inToMap(String[] unit, TreeMap<String, TreeMap> paramMap) {
        if (unit.length > 0 && !unit[0].equals("")) {
            String key = unit[0];
            String[] array = Arrays.copyOfRange(unit, 1, unit.length);
            if (paramMap.containsKey(key)) {
                inToMap(array, paramMap.get(key));
            } else {
                TreeMap<String, TreeMap> newMap = new TreeMap<>();
                inToMap(array, newMap);
                paramMap.put(key, newMap);
            }
        }
    }

    /** Список подразделений сортированный по заданному направлению.
     * рекурсивный вызов
     * @param prefix префикс текущего подразделения
     * @param deepMap карта текущего подразделения
     * @param list заполняемый список подразделений
     * @param direction направление сортировки
     */
    private void fromMap(String prefix, TreeMap deepMap, List<String> list, boolean direction) {
        if (deepMap.size() != 0) {
            Set<String> setKey = direction ? deepMap.keySet() : deepMap.descendingKeySet();
            for (String key : setKey) {
                String newPrefix = prefix.equals("") ? key : String.format("%s\\%s", prefix, key);
                list.add(newPrefix);
                fromMap(newPrefix, (TreeMap) deepMap.get(key), list, direction);
            }
        }
    }



}
