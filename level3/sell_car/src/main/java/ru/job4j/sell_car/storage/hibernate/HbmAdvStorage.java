package ru.job4j.sell_car.storage.hibernate;

import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.models.Advert;

public class HbmAdvStorage extends HbmStorage implements AdvStorage {
   @Override
   public List<Advert> getAll() {
      return query(sf -> sf.createQuery("from Advert order by created desc", Advert.class).list());
   }

   @Override
   public Advert getById(Integer id) {
      return query(sf -> sf.get(Advert.class, id));
   }

   @Override
   public long save(Advert advert) {
      if (advert.getId() != null) {
         query(sf -> {
            sf.update(advert);
            return null;
         });
      } else {
         query(sf -> {
            sf.save(advert);
            return null;
         });
      }
      return advert.getId();
   }

   @Override
   public List<Advert> getAllWithFilter(Map<String, Object> filters) {
      StringBuilder where = new StringBuilder();
      Map<String, Object> params = new HashMap<>();
      for (Map.Entry<String, Object> str : filters.entrySet()) {
         if ("onDay".equals(str.getKey()) && "on".equals(str.getValue())) {
            where.append("and ").append("a.created > :created ");
            params.put("created", Timestamp.from(Instant.now().minus(1, ChronoUnit.DAYS)));
         }
         if ("likeModel".equals(str.getKey()) && "on".equals(str.getValue())) {
            where.append("and ").append("model like :model");
            params.put("model", "%" + filters.get("model") + "%");
         }
         if ("isPhoto".equals(str.getKey()) && "on".equals(str.getValue())) {
            where.append("and ").append("img is not null ");
         }
      }
      if (where.length() > 0) {
         where.replace(0, 3, "where");
      }
      String queryWithParamsFilter = String.format(
       "from Advert a join fetch a.car car left join fetch car.images img %s order by a.created desc",
       where.toString());
      return query(sf -> {
         Query<Advert> query = sf.createQuery(queryWithParamsFilter, Advert.class);
         for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
         }
         return query.list();
      });
   }
}
