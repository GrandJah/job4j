package ru.job4j.todolist.storage.hibernate;

import java.util.List;
import ru.job4j.todolist.models.Category;
import ru.job4j.todolist.storage.Storage;

public class HbmStorageCategory extends HbmStorage implements Storage<Category> {

   @Override
   public List<Category> getAll() {
      return query(sf ->
       sf.createQuery("from Category", Category.class).list());
   }

   @Override
   public Category create(Category entity) {
      return query(sf ->
       sf.get(Category.class, sf.save(entity)));
   }

   @Override
   public boolean update(Integer id, Category entity) {
      entity.setId(id);
      return query(sf -> {
         sf.update(entity);
         return sf.get(Category.class, entity.getId()).equals(entity);
      });
   }

   @Override
   public Category getById(Integer id) {
      return query(sf -> sf.get(Category.class, id));
   }
}
