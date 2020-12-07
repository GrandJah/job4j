package ru.job4j.todolist.storage.hibernate;

import java.util.List;
import ru.job4j.todolist.models.Item;
import ru.job4j.todolist.storage.Storage;
import ru.job4j.todolist.storage.hibernate.HbmStorage;

public class HbmStorageItem extends HbmStorage implements Storage<Item> {

   @Override
   public List<Item> getAll() {
      return query(sf ->
       sf.createQuery("from Item", Item.class).list());
   }

   @Override
   public Item create(Item item) {
      return query(sf ->
       sf.get(Item.class, sf.save(item)));
   }

   @Override
   public boolean update(Integer id, Item item) {
      item.setId(id);
      return query(sf -> {
         sf.update(item);
         return sf.get(Item.class, item.getId()).equals(item);
      });
   }

   @Override
   public Item getById(Integer id) {
      return query(sf -> sf.get(Item.class, id));
   }
}
