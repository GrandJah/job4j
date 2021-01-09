package ru.job4j.integration;

import java.sql.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
   @EqualsAndHashCode.Include
   private int id;

   private String name;

   private String description;

   private Timestamp created;

   public static Order of(String name, String description) {
      return Order.builder().description(description).name(name)
                  .created(Timestamp.from(Instant.now())).build();
   }
}
