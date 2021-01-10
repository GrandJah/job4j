package ru.job4j.tracker;

import java.util.List;

/**
 * Store interface.
 */
public interface Store {
  Item add(Item item);

  boolean replace(String id, Item item);

  boolean delete(String id);

  List<Item> findAll();

  List<Item> findByName(String key);

  Item findById(Integer id);
}
