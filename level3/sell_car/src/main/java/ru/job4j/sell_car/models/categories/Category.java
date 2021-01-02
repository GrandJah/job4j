package ru.job4j.sell_car.models.categories;

public interface Category {
   String text();

   String propertyName();

   CategoryValue[] getSetValues();

   CategoryValue getValue(String valueName);
}
