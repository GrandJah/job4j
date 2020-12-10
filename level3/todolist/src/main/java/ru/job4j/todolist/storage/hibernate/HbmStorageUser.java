package ru.job4j.todolist.storage.hibernate;

import java.util.List;
import ru.job4j.todolist.models.User;
import ru.job4j.todolist.storage.Storage;

public class HbmStorageUser extends HbmStorage implements Storage<User> {
   @Override
   public List<User> getAll() {
      return query(sf -> sf.createQuery("from User", User.class).list());
   }

   @Override
   public User create(User user) {
      return query(sf -> sf.get(User.class, sf.save(user)));
   }

   @Override
   public boolean update(Integer id, User user) {
      user.setId(id);
      return query(sf -> {
         sf.update(user);
         return sf.get(User.class, user.getId()).equals(user);
      });
   }

   @Override
   public User getById(Integer id) {
      return query(sf -> sf.get(User.class, id));
   }

   @Override
   public List<User> findByField(String field, String value) throws UnsupportedOperationException {
      return query(
       sf -> sf.createQuery(String.format("from User where %s =:value", field), User.class)
        .setParameter("value", value).list());
   }
}
