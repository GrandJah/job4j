package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

public class SpringDI {
  public static void main(String[] args) throws Tracker.ErrorValue {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.scan("ru.job4j.start", "ru.job4j.tracker");
    context.refresh();

    Tracker tracker1 = context.getBean(Tracker.class);
    Tracker tracker2 = context.getBean(Tracker.class);

    tracker1.add(new Item("Name"));

    System.out.println("tracker1: " + tracker1.findAll());
    System.out.println("tracker2: " + tracker2.findAll());
  }
}
