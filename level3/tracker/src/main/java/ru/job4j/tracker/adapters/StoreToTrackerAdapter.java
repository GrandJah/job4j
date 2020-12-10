package ru.job4j.tracker.adapters;

import java.util.ArrayList;
import ru.job4j.tracker.Store;
import ru.job4j.tracker.Tracker;
import ru.job4j.tracker.Item;

public final class StoreToTrackerAdapter implements Tracker {
   private final Store store;

   public StoreToTrackerAdapter(Store store) {
      this.store = store;
   }

   private <T extends Item> T convert(Item f, T t) {
      t.setName(f.getName());
      t.setDescription(f.getDescription());
      t.setCreated(f.getCreated());
      t.setId(f.getId());
      return t;
   }

   private ru.job4j.tracker.expire.Item convert(Item item) {
      return convert(item, new ru.job4j.tracker.expire.Item());
   }

   private Item convert(ru.job4j.tracker.expire.Item item) {
      return convert(item, new Item());
   }

   @Override
   public ru.job4j.tracker.expire.Item add(ru.job4j.tracker.expire.Item item) throws ErrorValue {
      return convert(store.add(convert(item)));
   }

   @Override
   public ru.job4j.tracker.expire.Item findById(String id) throws NotFound {
      return convert(store.findById(id));
   }

   @Override
   public ru.job4j.tracker.expire.Item findById(Integer id) throws NotFound {
      return convert(store.findById(String.valueOf(id)));
   }

   @Override
   public void update(ru.job4j.tracker.expire.Item item) throws NotFound, ErrorValue {
      store.replace(item.getId().toString(), item);
   }

   @Override
   public void delete(ru.job4j.tracker.expire.Item item) throws NotFound, ErrorValue {
      store.delete(item.getId().toString());
   }

   @Override
   public ru.job4j.tracker.expire.Item findByName(String key) throws NotFound {
      return convert(store.findByName(key).get(0));
   }

   @Override
   public ArrayList<ru.job4j.tracker.expire.Item> getAll() {
      ArrayList<ru.job4j.tracker.expire.Item> result = new ArrayList<>();
      store.findAll().forEach(i -> result.add(convert(i)));
      return result;
   }
}
