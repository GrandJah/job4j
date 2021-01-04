package ru.job4j.candidate.storage;


import java.util.List;
import lombok.extern.log4j.Log4j2;
import ru.job4j.candidate.models.Candidate;

@Log4j2
public class HibernateRunCandidate extends HbmStorage {
   public static void main(String[] args) {
      HibernateRunCandidate app = new HibernateRunCandidate();
      Candidate first = Candidate.of("Fedor Grishin", 3, 50000);
      Candidate second = Candidate.of("Vasiliy Peskov", 10, 80000);
      Candidate third = Candidate.of("Zulfia Kirova", 5, 70000);
      log.info("{}, {}, {}", first, second, third);
      app.save(first);
      app.save(second);
      app.save(third);
      for (Object st : app.getAll()) {
         log.info(st);
      }
      log.info(app.findById(1));
      log.info(app.findById(2));
      log.info(app.findById(3));
      log.info(app.findByName("Fedor Grishin"));
      log.info(app.findByName("Vasiliy Peskov"));
      log.info(app.findByName("Zulfia Kirova"));
      app.updSalaryAndExp(2, 100000, 8);
      log.info(app.findById(2));
      app.delete(1);
      app.delete(2);
      app.delete(3);
      log.info(app.getAll());


   }

   public List<Candidate> getAll() {
      return query(sf -> sf.createQuery("from Candidate").list());
   }

   public Candidate findById(Integer id) {
      return (Candidate) query(
       sf -> sf.createQuery("from Candidate c where c.id = :cid").setParameter("cid", id)
               .uniqueResult());
   }

   public Candidate findByName(String name) {
      return (Candidate) query(
       sf -> sf.createQuery("from Candidate c where c.name = :name").setParameter("name", name)
               .uniqueResult());
   }

   public void updSalaryAndExp(Integer id, Integer salary, Integer experience) {
      query(sf -> sf.createQuery(
       "update Candidate c set c.salary =:salary, c.experience =:experience where c.id = :id")
                    .setParameter("id", id).setParameter("salary", salary)
                    .setParameter("experience", experience).executeUpdate());
   }

   public void delete(Integer id) {
      query(sf -> sf.createQuery(" delete from Candidate c where c.id = :id").setParameter("id", id)
                    .executeUpdate());
   }
}
