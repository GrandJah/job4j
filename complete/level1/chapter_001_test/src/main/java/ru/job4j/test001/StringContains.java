package ru.job4j.test001;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class StringContains {
    /**
     * @param origin строка запроса
     * @param sub проверяемая подстрока
     * @return содержится ли строка
     */
    boolean contains(String origin, String sub) {
        char[] charOrigin = origin.toCharArray();
        char[] charSub = sub.toCharArray();
        boolean contains = false;
        for (int index = 0; index < charOrigin.length; index++) {
            if (charOrigin.length - index >= charSub.length) {
                for (int subIndex = 0; subIndex < charSub.length; subIndex++) {
                    if (charOrigin[index + subIndex] != charSub[subIndex]) {
                        break;
                    } else if (subIndex == charSub.length - 1) {
                        contains = true;
                    }
                }
            } else {
                break;
            }
        }
        return contains;
    }
}
