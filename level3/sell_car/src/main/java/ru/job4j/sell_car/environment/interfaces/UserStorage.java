package ru.job4j.sell_car.environment.interfaces;

import ru.job4j.sell_car.models.User;

public interface UserStorage {
   User findByName(String user);

   User save(User of);
}
