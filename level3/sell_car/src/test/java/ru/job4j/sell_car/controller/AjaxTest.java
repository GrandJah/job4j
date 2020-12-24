package ru.job4j.sell_car.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

public class AjaxTest {

   private static final Logger LOG = Logger.getLogger(AjaxTest.class);
   private static final Map<String, String> VALUES = new HashMap<>();

   /**
    * main test method.
    *
    * @param json json action
    * @throws IOException IOException
    */
   private void t(String json, String expectTemplate) throws IOException {
      LOG.debug("JSON: " + json + " /");
      BufferedReader reader = mock(BufferedReader.class);
      when(reader.readLine()).thenReturn(json);

      HttpServletRequest req = mock(HttpServletRequest.class);
      when(req.getReader()).thenReturn(reader);

      PrintWriter writer = mock(PrintWriter.class);
      AtomicReference<String> answer = new AtomicReference<>();
      AtomicReference<String> expect = new AtomicReference<>();

      doAnswer(invocationOnMock -> {
         String strArg = invocationOnMock.getArgumentAt(0, String.class);
         expect.set(extractValues(expectTemplate, strArg));
         answer.set(strArg);
         return null;
      }).when(writer).write(anyString());

      HttpServletResponse resp = mock(HttpServletResponse.class);
      when(resp.getWriter()).thenReturn(writer);

      new Ajax().doPost(req, resp);

      assertEquals(expect.get(), answer.get());
      LOG.debug("ok!");
   }

   private String injectValues(String expectTemplate) {
      String actual = expectTemplate;
      int i = 0;
      while (true) {
         i = expectTemplate.indexOf("${", i + 1);
         int e = expectTemplate.indexOf("}", i);
         if (i == -1) {
            break;
         }
         String currentValue = expectTemplate.substring(i + 2, e);
         actual = actual.replace(String.format("${%s}", currentValue), VALUES.get(currentValue));
      }
      return actual;
   }

   private String extractValues(String template, String actual) {
      int i = 0;
      int delta = 0;
      while (true) {
         i = template.indexOf("${", i + 1);
         int e = template.indexOf("}", i);
         if (i == -1) {
            break;
         }
         String currentValue = template.substring(i + 2, e);
         int j = actual.length();
         String[] search = new String[]{"}", ",", "\""};
         for (String s : search) {
            int q = actual.indexOf(s, i + delta);
            if (q > 0) {
               j = Math.min(q, j);
            }
         }
         String extract = actual.substring(i + delta, j);
         VALUES.putIfAbsent(currentValue, extract);
         delta = j - e - 1;
      }
      return injectValues(template);
   }

   String p(String key, String value) {
      String valueMap = VALUES.get(value);
      return valueMap == null ? "" : String.format("'%s':'%s'", key, valueMap);
   }

   private String act(String action) {
      return String.format("'action':'%s'", action);
   }

   private String ok() {
      return "'success':true";
   }

   private String fail(String error) {
      return String.format("'success':false,'error':'%s'", error);
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
      t(q(act("list_ad")), q("'data':[]", ok()));
      t(q(act("login")), q(fail("errorUser")));
      t(q(act("login"), "'username':'noLogin', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'login', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'noLogin', 'pass':'pass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'login', 'pass':'pass'"), q(fail("errorUser")));
      t(q(act("register"), "'username':'login', 'pass':'pass'"), q(ok(), "'token':'${token1-1}'"));
      t(q(act("register"), "'username':'login', 'pass':'pass'"), q(fail("errorName")));
      t(q(act("login")), q(fail("errorUser")));
      t(q(act("login"), "'username':'noLogin', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'login', 'pass':'noPass'"), q(fail("errorUser")));
      t(q(act("login"), "'username':'noLogin', 'pass':'pass'"), q(fail("errorUser")));
      t(q(act("register"), "'username':'login2', 'pass':'pass2'"),
       q(ok(), "'token':'${token2-1}'"));
      t(q(act("login"), "'username':'login', 'pass':'pass'"), q(ok(), "'token':'${token1-2}'"));
      t(q(act("list_ad"), p("token", "token1-1")), q("'data':[]", ok()));
      //todo добавление объявлений
      t(q(act("create"), p("token", "token1-1")), q(fail("not authentication")));
      t(q(act("create"), p("token", "token1-2")), q(fail("create - unknown error")));
      t(q(act("changeStatus"), p("token", "token1-2"), "'id':23423"), q(fail("invalid ID")));
      t(q(act("changeStatus"), p("token", "token1-2"), "'id':'23423'"), q(fail("invalid ID")));
      String advert1 = "advert:{}"; //user - "login"
      t(q(act("create"), p("token", "token1-2"), advert1), q("'id_adv':${id_adv1}", ok()));
      t(q(act("list_ad")), q(
       "'data':[{'price':0,'created':'${regAdv1}','description':'','id':${id_adv1},'user':{'name':'login','registration':'${regLogin1}'},'status':false}]",
       ok()));
      t(q(act("changeStatus"), p("token", "token1-2"), p("id", "id_adv1")),
       q(ok(), "'status':true"));
      t(q(act("list_ad")), q(
       "'data':[{'price':0,'created':'${regAdv1}','description':'','id':${id_adv1},'user':{'name':'login','registration':'${regLogin1}'},'status':true}]",
       ok()));
      t(q(act("changeStatus"), p("token", "token1-2"), p("id", "id_adv1")),
       q(ok(), "'status':false"));
      t(q(act("list_ad")), q(
       "'data':[{'price':0,'created':'${regAdv1}','description':'','id':${id_adv1},'user':{'name':'login','registration':'${regLogin1}'},'status':false}]",
       ok()));


      //todo устаревший токен
      t(q(act("login"), "'username':'login2', 'pass':'pass2'"), q(ok(), "'token':'${token2-2}'"));
   }

   @Test
   public void testWithDefaultEnvironment() throws IOException {
      new Environment() {
         {
            changeEnvironment(Environment.DEFAULT_ENVIRONMENT);
         }
      };
      testSessionAjax();
   }

   @Test
   public void testWithTestEnvironment() throws IOException {
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
      testSessionAjax();
   }

}
