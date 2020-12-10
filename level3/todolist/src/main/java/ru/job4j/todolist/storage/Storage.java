package ru.job4j.todolist.storage;

import java.util.List;

public interface Storage<Type> {
   List<Type> getAll();

   Type create(Type entity);

   boolean update(Integer id, Type entity);

   Type getById(Integer id);

   default List<Type> findByField(String field, String value) throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }
}
