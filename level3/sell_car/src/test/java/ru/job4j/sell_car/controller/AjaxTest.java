package ru.job4j.sell_car.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
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

   /**
    * main test method.
    *
    * @param json   json action
    * @param expect answer json
    * @param user   user session
    * @throws IOException IOException
    */
   private void test(String json, String expectTemplate, AtomicReference<User> user) throws IOException {
      LOG.debug("JSON: " + json + " /");
      AtomicReference<String> answer = new AtomicReference<>();
      HttpServletRequest req = mock(HttpServletRequest.class);
      HttpServletResponse resp = mock(HttpServletResponse.class);

//      BufferedReader reader = mock(BufferedReader.class);
//      when(req.getReader()).thenReturn(reader);
//      when(reader.readLine()).thenReturn(json);
//      PrintWriter writer = mock(PrintWriter.class);
//      when(resp.getWriter()).thenReturn(writer);
//      HttpSession session = mock(HttpSession.class);
//      when(req.getSession()).thenReturn(session);
//      when(session.getAttribute(eq("user"))).thenReturn(user.get());
//
//      doAnswer(invocationOnMock -> {
//         user.set(invocationOnMock.getArgumentAt(1, User.class));
//         return null;
//      }).when(session).setAttribute(eq("user"), any());
//      doAnswer(invocationOnMock -> {
//         answer.set(invocationOnMock.getArgumentAt(0, String.class));
//         return null;
//      }).when(writer).write(anyString());

      new Ajax().doPost(req, resp);

      String actual = answer.get();
      String expect = expectTemplate.replace("${token}", getToken("login"));
      assertEquals(expect, actual);
      LOG.debug("ok!");
   }

   private String getToken(String username) {
      Environment env = Environment.inst(this);
      User user = env.get(UserStorage.class).findByName(username);
      return env.get(Shadows.class).findByUser(user).getToken();
   }

   private String action(String action) {
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
      test(q(action("list_ad")), q("'data':[]", ok()), user);
      test(q(action("login")), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'noLogin', 'pass':'noPass'"), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'login', 'pass':'noPass'"), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'noLogin', 'pass':'pass'"), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'login', 'pass':'pass'"), q(fail("errorUser")), user);
      //todo регистрация и добавление юзера
      test(q(action("register"), "'username':'login', 'pass':'pass'"), q(ok(), "'token':'${token}!'"), user);


      //todo тесты после добавления пользователя
      test(q(action("login")), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'noLogin', 'pass':'noPass'"), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'login', 'pass':'noPass'"), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'noLogin', 'pass':'pass'"), q(fail("errorUser")), user);
      test(q(action("login"), "'username':'login', 'pass':'pass'"), q(ok()), user);
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
