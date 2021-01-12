package ru.job4j.di;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Context {
  private Map<String, Object> environment = new HashMap<>();

  public void reg(Class classForRegister) {
    Constructor[] constructors = classForRegister.getDeclaredConstructors();
    if (constructors.length > 1) {
      throw new IllegalStateException(
        "Class has multiple constructors : " + classForRegister.getCanonicalName());
    }
    Constructor constructor = constructors[0];
    List args = new ArrayList<>();
    for (Class arg : constructor.getParameterTypes()) {
      if (!environment.containsKey(arg.getCanonicalName())) {
        throw new IllegalStateException(
          "Object doesn't found in context : " + arg.getCanonicalName());
      }
      args.add(environment.get(arg.getCanonicalName()));
    }
    try {
      Set<String> classes = new HashSet<>();
      Class currentClassType = classForRegister;
      do {
        classes.add(currentClassType.getCanonicalName());
        for (Class classInterface : currentClassType.getInterfaces()) {
          classes.add(classInterface.getCanonicalName());
        }
        currentClassType = currentClassType.getSuperclass();
      } while (currentClassType != Object.class);
      Object instance = constructor.newInstance(args.toArray());
      classes.forEach(nameClass -> environment.put(nameClass, instance));
    } catch (Exception e) {
      throw new IllegalStateException(
        "Coun't create an instance of : " + classForRegister.getCanonicalName(), e);
    }
  }

  public <T> T get(Class<T> instance) {
    return (T) environment.get(instance.getCanonicalName());
  }
}
