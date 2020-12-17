package ru.job4j.sell_car.models;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = "content")
@EqualsAndHashCode(exclude = "content")
@Entity
@Table(name = "file_uploads")
public class ImageFile {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String filepath;

   private String type;

   private Timestamp created = Timestamp.from(Instant.now());

   private Integer size = 0;

   @Transient
   private byte[] content = new byte[0];

   public void setContent(byte[] content) {
      this.size = content.length;
      this.content = content;
   }
}
