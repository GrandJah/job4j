package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.job4j.start.ConsoleInput;
import ru.job4j.start.StartUI;
import ru.job4j.tracker.TrackerArray;

public class SpringDI {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(TrackerArray.class);
    context.register(ConsoleInput.class);
    context.register(StartUI.class);
    context.refresh();

    StartUI ui = context.getBean(StartUI.class);
    ui.run();
  }
}
