package ru.job4j.todolist.utility;

import java.util.Optional;
import ru.job4j.todolist.models.Category;
import ru.job4j.todolist.storage.hibernate.HbmStorage;

public class AddEntityToDataBase extends HbmStorage {

   public static void main(String[] args) {
      new AddEntityToDataBase().run();
   }

   private void run() {
      String[] listCategories = {
       "Новая", "Важная", "Срочная", "По теме", " Для внутреннего пользования", "Нет идей",
       "Для того чтобы была", "После прочтения съесть и сжечь!"
      };
      for (String name : listCategories) {
         addCategory(name);
      }
   }

   private Category addCategory(String name) {
      return query(sf -> {
         Category category = Optional.ofNullable(
          sf.createQuery("from Category where name = :name", Category.class)
            .setParameter("name", name).uniqueResult()).orElse(Category.of(name));
         sf.persist(category);
         return category;
      });
   }

}
