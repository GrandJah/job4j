package ru.job4j.sell_car.controller.stub;

import java.util.ArrayList;
import java.util.List;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.models.Advert;

public class StubAdvStorage implements AdvStorage {
   private static final List<Advert> ADVERTS = new ArrayList<>();
   @Override
   public List<Advert> getAll() {
      return ADVERTS;
   }
}
