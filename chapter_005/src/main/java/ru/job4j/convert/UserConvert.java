package ru.job4j.convert;

import java.util.HashMap;
import java.util.List;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class UserConvert {
    /** который принимает в себя список пользователей
     * и конвертирует его в Map с ключом Integer id и соответствующим ему User.
     * @param list список пользователей
     * @return Карта пользователей
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> map = new HashMap<>();
        for (User user : list) {
            map.put(user.getId(), user);
        }
        return map;
    }
}
