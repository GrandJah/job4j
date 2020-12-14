package models;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "history_owner")
public class HistoryOwner {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @JoinColumn(name = "id")
   private Integer id;

   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "driver_id", nullable = false)
   @NotNull
   private Driver driver;

   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "car_id", nullable = false)
   @NotNull
   private Car car;


   private Timestamp startOwner;

   private Timestamp endOwner;
}
