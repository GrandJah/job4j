package ru.job4j.di;

import ru.job4j.start.ConsoleInput;
import ru.job4j.start.StartUI;
import ru.job4j.tracker.TrackerArray;

public class Main {
  public static void main(String[] args) {
    Context context = new Context();
    context.reg(TrackerArray.class);
    context.reg(ConsoleInput.class);
    context.reg(StartUI.class);

    StartUI ui = context.get(StartUI.class);
    ui.run();
  }
}
