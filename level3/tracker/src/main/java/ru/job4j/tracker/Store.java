package ru.job4j.tracker;

import java.util.List;

/**
 * Store interface.
 */
public interface Store extends Tracker {

  @Override
  default Item findByNameFirst(String key) {
    return findByName(key).get(0);
  }

  List<Item> findByName(String key);
}
