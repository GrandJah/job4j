package ru.job4j.sell_car.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.log4j.Log4j;
import ru.job4j.sell_car.controller.stub.StabFileStorage;
import ru.job4j.sell_car.controller.stub.StubAdvStorage;
import ru.job4j.sell_car.controller.stub.StubShadows;
import ru.job4j.sell_car.controller.stub.StubUserStorage;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.models.User;

@Log4j
public class AjaxTest {

   /**
    * main test method.
    *
    * @param json json action
    * @throws IOException IOException
    */
   private void t(String json, String expectTemplate) throws IOException {
      log.debug("JSON: " + json + " /");
      TestJSONServlet test = new TestJSONServlet(json, expectTemplate);
      new Ajax().doPost(test.request(), test.response());
      assertEquals(test.expect(), test.answer());
      log.debug("ok!");
   }

   String p(String key, String value) {
      String valueMap = TestJSONServlet.get(value);
      return valueMap == null ? "" : String.format("'%s':'%s'", key, valueMap);
   }

   private String act(String action) {
      return String.format("'action':'%s'", action);
   }

   private String ok() {
      return "'success':true";
   }

   private String fail(String error) {
      return String.format("'error':'%s','success':false", error);
   }

   private String q(String... str) {
      StringBuilder builder = new StringBuilder();
      builder.append("{");
      for (String string : str) {
         builder.append(string);
         builder.append(",");
      }
      builder.deleteCharAt(builder.length() - 1);
      builder.append("}");
      return builder.toString().replace("'", "\"");
   }

   /**
    * test method.
    *
    * @throws IOException IOException
    */
   public void testSessionAjax() throws IOException {
      AtomicReference<User> user = new AtomicReference<>();
      //Пустая база
      t(q(act("list_ad")), q("'data':[]", ok()));
      //Отсутствуют данные
      t(q(act("login")), q(fail("errorUser")));
      //Неправильный пользователь
      t(q(act("login"), "'username':'noLogin', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'login', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'noLogin', 'pass':'pass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'login', 'pass':'pass'"), q(fail("errorUser")));
      //Регистрация пользователя выдача токена
      t(q(act("register"), "'username':'login', 'pass':'pass'"), q(ok(), "'token':'${token1-1}'"));
      //Повторная регистрация существующего логина
      t(q(act("register"), "'username':'login', 'pass':'pass'"), q(fail("errorName")));
      //Вход с неверными данными с существующим пользователем
      t(q(act("login")), q(fail("errorUser")));
      t(q(act("login"), "'username':'noLogin', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'login', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'noLogin', 'pass':'pass'"), q(fail("errorUser")));
      //Регистрация второго пользователя
      t(q(act("register"), "'username':'login2', 'pass':'pass2'"),
       q(ok(), "'token':'${token2-1}'"));
      //Повторный вход со сменой токена
      t(q(act("login"), "'username':'login', 'pass':'pass'"), q(ok(), "'token':'${token1-2}'"));
      //Запрос данных с токеном
      t(q(act("list_ad"), p("token", "token1-1")), q("'data':[]", ok()));
      //Попытка добавления с неверными данными
      t(q(act("create"), p("token", "token1-1")), q(fail("not authentication")));
      t(q(act("create"), p("token", "token1-2")), q(fail("create - unknown error")));
      //Попытка смены статуса с неверными данными
      t(q(act("changeStatus"), p("token", "token1-2"), "'id':23423"), q(fail("invalid ID")));
      t(q(act("changeStatus"), p("token", "token1-2"), "'id':'23423'"), q(fail("invalid ID")));
      //user - "login" - новое объявление
      String advert1 = "advert:{}";
      t(q(act("create"), p("token", "token1-2"), advert1), q("'id_adv':${id_adv1}", ok()));
      String advert1AnswerStatusFormat = "{'created':'${regAdv1}','description':'','id':${id_adv1},"
       + "'price':0,'status':%s,'user':{'id':${idLogin1},'name':'login','registration':'${regLogin1}'}}";
      t(q(act("list_ad")),
       q(String.format("'data':[%s]", String.format(advert1AnswerStatusFormat, "false")), ok()));
      //Смена статуса
      t(q(act("changeStatus"), p("token", "token1-2"), p("id", "id_adv1")),
       q("'status':true", ok()));
      t(q(act("list_ad")),
       q(String.format("'data':[%s]", String.format(advert1AnswerStatusFormat, "true")), ok()));
      t(q(act("changeStatus"), p("token", "token1-2"), p("id", "id_adv1")),
       q("'status':false", ok()));
      t(q(act("list_ad")),
       q(String.format("'data':[%s]", String.format(advert1AnswerStatusFormat, "false")), ok()));
      t(q(act("login"), "'username':'login2', 'pass':'pass2'"), q(ok(), "'token':'${token2-2}'"));
   }

   private void setDefaultEnvironment() {
      TestJSONServlet.clear();
      new Environment() {
         {
            changeEnvironment(Environment.DEFAULT_ENVIRONMENT);
         }
      };
   }

   @Test
   public void testWithDefaultEnvironment() throws IOException {
      setDefaultEnvironment();
      testSessionAjax();
   }

   private void setTestEnvironment() {
      TestJSONServlet.clear();
      new Environment() {
         {
            changeEnvironment(new HashMap<Class, Class>() {
               {
                  put(FileStorage.class, StabFileStorage.class);
                  put(AdvStorage.class, StubAdvStorage.class);
                  put(UserStorage.class, StubUserStorage.class);
                  put(Shadows.class, StubShadows.class);
                  //            put(CarStorage.class, null);
                  //            put(Upload.class, null);
               }
            });
         }
      };
   }

   @Test
   public void testWithTestEnvironment() throws IOException {
      setTestEnvironment();
      testSessionAjax();
   }

   @Test
   public void testGetCategories() throws IOException {
      setTestEnvironment();
      log.debug("test");
      TestJSONServlet test = new TestJSONServlet(q(act("getCategories")), q("'categories':{"
        + "'car_type':['CONVERTIBLE','COUPE','HATCHBACK','MINIVAN','PICKUP','SEDAN','SUV','VAN','WAGON'],"
        + "'fuel_type':['DIESEL','LIQUEFIED_GAS','PETROL'],"
        + "'gear_type':['AUTO','MANUAL','NONE']," + "'wd_type':['FrontWD','FullWD','RearWD']}",
       ok()));
      new Ajax().doPost(test.request(), test.response());
      assertEquals(test.expect(), test.answer());
      log.debug("ok!");
   }

}
