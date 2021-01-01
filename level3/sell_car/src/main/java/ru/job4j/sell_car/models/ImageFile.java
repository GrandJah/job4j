package ru.job4j.sell_car.models;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "file_uploads")
public class ImageFile {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @JoinColumn(name = "filepath", unique = true, nullable = false)
   private String filepath;

   private Timestamp created = Timestamp.from(Instant.now());
}
