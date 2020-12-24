package ru.job4j.sell_car.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestJSONServlet {
   private static Map<String, String> VALUES = new HashMap<>();


   private HttpServletRequest req = mock(HttpServletRequest.class);
   private HttpServletResponse resp = mock(HttpServletResponse.class);
   private final AtomicReference<String> expectVariable = new AtomicReference<>();
   private final AtomicReference<String> answerVariable = new AtomicReference<>();

   TestJSONServlet(String json, String expectTemplate) throws IOException {
      BufferedReader reader = mock(BufferedReader.class);
      when(reader.readLine()).thenReturn(json);
      when(req.getReader()).thenReturn(reader);

      PrintWriter writer = mock(PrintWriter.class);

      doAnswer(invocationOnMock -> {
         String string = invocationOnMock.getArgumentAt(0, String.class);
         try {
            String strArg = sortJSONObject(string);
            expectVariable.set(extractValues(expectTemplate, strArg));
            answerVariable.set(strArg);
         }catch (Exception e) {
            System.out.println(string);
            System.out.println(expectTemplate);
            throw new RuntimeException("error convert test string");
         }
         return null;
      }).when(writer).write(anyString());
      when(resp.getWriter()).thenReturn(writer);
   }

   public static void clear() {
      VALUES = new HashMap<>();
   }

   public static String get(String value) {
      return VALUES.get(value);
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

   public HttpServletRequest request() {
      return req;
   }

   public HttpServletResponse response() {
      return resp;
   }

   public String expect() {
      return expectVariable.get();
   }

   public String answer() {
      return answerVariable.get();
   }

   private String sortJSONObject(String sortString) {
      if (!sortString.contains(",")) {
         return sortString;
      }
      int currentLevel = 0;
      int[] delimiter = new int[500];
      int delimiterIndex = 0;
      char startChar = sortString.charAt(0);
      boolean object = startChar == '{' || startChar == '[';
      if (!object) {
         int indProp = sortString.indexOf(":");
         String prop = sortString.substring(0, indProp + 1);
         String val = sortString.substring(indProp + 1);
         String sorted = sortJSONObject(val);
         return String.format("%s%s", prop, sorted);
      }
      for (int index = 1; index < sortString.length(); index += 1) {
         switch (sortString.charAt(index)) {
            case '{':
            case '[':
               currentLevel += 1;
               break;
            case '}':
            case ']':
               currentLevel -= 1;
               break;
            case ',':
               if (currentLevel == 0) {
                  delimiter[delimiterIndex++] = index;
               }
               break;
         }
      }
      LinkedList<String> list = new LinkedList<>();
      int startIndex = 1;
      for (int i = 0; i < delimiterIndex; i += 1) {
         String str = sortString.substring(startIndex, delimiter[i]);
         startIndex = delimiter[i] + 1;
         list.add(str);
      }
      list.add(sortString.substring(startIndex, sortString.length() - 1));
      LinkedList<String> completeList = new LinkedList<>();
      list.forEach(str -> completeList.add(sortJSONObject(str)));
      completeList.sort(Comparator.naturalOrder());
      String complete = String.join(",", completeList);
      complete = String.format("%s%s%s", sortString.charAt(0), complete,
       sortString.charAt(sortString.length() - 1));
      return complete;
   }
}
