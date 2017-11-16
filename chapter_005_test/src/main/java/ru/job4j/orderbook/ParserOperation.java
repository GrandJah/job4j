package ru.job4j.orderbook;


import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 16.11.2017
 */
class ParserOperation {
    /**
     * Структура значения таблицы заказов.
     */
    private class OrderEntry {
        /**
         * Название книги.
         */
        private String book;
        /**
         * Атрибуты операции.
         */
        private Attribute attribute;
    }

    /**
     * Таблица найденных заказов.
     */
    private Map<Integer, OrderEntry> operationBook;

    /** Парсер файла xml.
     * @param filePath путь к файлу xml
     * @return Списки атрибутов заказов разбитые по названию книг
     */
    Map<String, List<Attribute>> parse(String filePath) {
        this.operationBook = new HashMap<>();
        XMLReader reader = new SAXParser();
        reader.setContentHandler(new Parser());

        try {
            reader.parse(filePath);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        Map<String, List<Attribute>> retMap = new TreeMap<>();
        for (OrderEntry order : this.operationBook.values()) {
            String book = order.book;

            if (retMap.containsKey(book)) {
                retMap.get(book).add(order.attribute);
            } else {
                List<Attribute> list = new LinkedList<>();
                list.add(order.attribute);
                retMap.put(book, list);
            }
        }
        return retMap;
    }

    /**
     * Парсер.
     */
    private class Parser extends DefaultHandler {

        @Override
        public void startElement(String uri, String tagName, String rawName, Attributes attributes) {
            OrderEntry orderEntry;
            switch (tagName) {
                case "AddOrder":
                    int id = Integer.parseInt(attributes.getValue("orderId"));
                    String book = attributes.getValue("book");
                    orderEntry = new OrderEntry();
                    orderEntry.book = book;
                    orderEntry.attribute = new Attribute(attributes.getValue("operation").equals("BUY"),
                            Double.parseDouble(attributes.getValue("price")),
                            Integer.parseInt(attributes.getValue("volume")));
                    operationBook.put(id, orderEntry);
                    break;
                case "DeleteOrder":
                    id = Integer.parseInt(attributes.getValue("orderId"));
                    book = attributes.getValue("book");
                    orderEntry = operationBook.remove(id);
                    if (!orderEntry.book.equals(book)) {
                        System.out.println("delete id:" + id + " - error book name \'" + book + "\'!");
                    }
                    break;
                default:
            }
        }
    }

}
