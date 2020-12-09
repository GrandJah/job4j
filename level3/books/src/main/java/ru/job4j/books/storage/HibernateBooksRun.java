package ru.job4j.books.storage;


import java.util.Optional;
import ru.job4j.books.models.Author;
import ru.job4j.books.models.Book;

public class HibernateBooksRun extends HbmStorage {
   public static void main(String[] args) {
      HibernateBooksRun app = new HibernateBooksRun();
      Author removeAuthor = app.createEntry("Klassikov",
       "Volna", "Dom na beregu", "More i step'", "Drug", "Vinil", "Belka");
      app.createEntry("Prozaikin",
       "Poema o slone", "Solnce v zakate", "More i step'", "Vinil");
      app.remove(removeAuthor);
   }

   public Author createEntry(String authorName, String... books) {
      return query(sf -> {
         Author author = Optional.ofNullable(sf.createQuery("from Author where name = :name", Author.class)
          .setParameter("name", authorName).uniqueResult()).orElse(Author.ofName(authorName));
         for (String bookName : books) {
            author.addBook(Optional.ofNullable(sf.createQuery("from Book where name = :name",
             Book.class).setParameter("name", bookName).uniqueResult()).orElse(Book.ofName(bookName)));
         }
         sf.persist(author);
         return author;
      });
   }
}
