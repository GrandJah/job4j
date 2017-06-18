package ru.job4j.unitsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.06.2017
 */
public class UnitSort implements Comparable<UnitSort> {
    /**
     * Код подразделения.
     */
    private String name;

    /**
     * Карта подразделений.
     */
    private TreeSet<UnitSort> units;

    /**
     * Напрвавление сортировки.
     */
    static final boolean  ASCENT = true, DESCENT = false;

    /**
     * Default constructor.
     */
    public UnitSort() {
        init("");
    }

    /**
     * @param name Код подразделения.
     */
    private UnitSort(String name) {
        init(name);
    }

    /**
     * @param name Код подразделения.
     */
    private void init(String name) {
        this.name = name;
        this.units = new TreeSet<>();
    }

    /** Сортировка по направлению.
     * @param direction напрвление сортировки
     * @return сортированный список
     */
    public String[] sortAction(boolean direction) {
        List<String> list = new ArrayList<>();
        fromSet("", this.units, list, direction);
        return list.toArray(new String[list.size()]);
    }

    /** Добавление подразделения в карту подразделений.
     * @param units подразделения
     */
    public void addAll(String[] units) {
        for (String unit : units) {
            add(unit);
        }
    }

    /** Добавление подразделения в карту подразделений.
     * @param unit подразделение
     */
    private void add(String unit) {
        inToMap(unit.split("\\\\"), this.units);
    }

    /** Добавление подразделения в карту подразделений.
     * рекурсивный поиск места для вставки
     * @param unit добавляемое подразделение
     * @param paramSet карта текущего подразделения
     */
    private void inToMap(String[] unit, TreeSet<UnitSort> paramSet) {
        if (unit.length > 0 && !unit[0].equals("")) {
            String keyName = unit[0];
            String[] array = Arrays.copyOfRange(unit, 1, unit.length);
            UnitSort newUnit = new UnitSort(keyName);
            if (!paramSet.contains(newUnit)) {
                paramSet.add(newUnit);
            }
            inToMap(array, paramSet.floor(newUnit).units);
        }
    }

    /** Список подразделений сортированный по заданному направлению.
     * рекурсивный вызов
     * @param prefix префикс текущего подразделения
     * @param deepSet карта текущего подразделения
     * @param list заполняемый список подразделений
     * @param direction направление сортировки
     */
    private void fromSet(String prefix, TreeSet deepSet, List<String> list, boolean direction) {
        if (deepSet.size() != 0) {
            Iterator setIt = direction ? deepSet.iterator() : deepSet.descendingIterator();
            while (setIt.hasNext()) {
                UnitSort deepUnit = (UnitSort) setIt.next();
                String newPrefix = prefix.equals("") ? deepUnit.name : String.format("%s\\%s", prefix, deepUnit.name);
                list.add(newPrefix);
                fromSet(newPrefix, deepUnit.units, list, direction);
            }
        }
    }

    @Override
    public int compareTo(UnitSort o) {
        return this.name.compareTo(o.name);
    }
}
