package ru.job4j.string_mathing;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Task class.
 */
public class Match {
    /** Сравнение строк перебором сортированных символов.
     * @param a строка a
     * @param b строка b
     * @return true if ok.
     */
    public boolean compareChar(String a, String b) {
        boolean ok = a.length() == b.length();
        if (ok) {
            char[] ca = a.toCharArray();
            char[] cb = b.toCharArray();
            Arrays.sort(ca);
            Arrays.sort(cb);
            for (int i = 0; i < ca.length; i++) {
                if (ca[i] != cb[i]) {
                    ok = false; break;
                }
            }
        }
        return ok;
    }

    /** Сравнение строк через сравнение кортежа коллекции.
     * @param a строка a
     * @param b строка b
     * @return true if ok.
     */
    public boolean compareList(String a, String b) {
        return a.length() == b.length() && stringToSortList(a).equals(stringToSortList(b));
    }

    /** перевод строки в коллекцию символов.
     * @param string исходная строка
     * @return коллекция символов.
     */
    private ArrayList<String> stringToSortList(String string) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(string.split("")));
        list.sort(String::compareTo);
        return list;
    }
}
