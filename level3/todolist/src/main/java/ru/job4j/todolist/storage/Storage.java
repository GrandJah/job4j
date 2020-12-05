package ru.job4j.todolist.storage;

import java.util.List;

public interface Storage<Type> {
   List<Type> getAll();
   Type create(Type item);
   boolean update(Integer id, Type item);
   Type getById(Integer id);
}
