package ru.job4j.vacancy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.job4j.res.Configure;
import ru.job4j.res.DB;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 23.12.2017
 */
public class StartSystem {
    /**
     * конфигурация.
     */
    private Configure conf;
    /**
     * время последнего запуска.
     */
    private long lastStart;
    /**
     * Хранилище.
     */
    private DB db;

    /**
     * @param config файл конфигурации
     */
    StartSystem(String config) {
        this.conf = new Configure(config);
        this.db = new DB(conf);
    }

    /** главный метод системы.
     * @return Время до следуюшего старта
     */
    public long start() {
        long start = System.currentTimeMillis();
        boolean parseActive = true;
        int page = 0;
        this.lastStart = this.db.checkLastStart();
        try {
            if (System.currentTimeMillis() - lastStart > getInterval()) {
                while (parseActive) {
                    Document doc = Jsoup.connect(this.conf.get("url") + ++page).get();
                    for (Element element : doc.getElementsByClass("postslisttopic")) {
                        if (!parseElement(element)) {
                            parseActive = false;
                            break;
                        }
                    }
                }
            }
            this.db.setLastStart(start);
            this.conf.log("Все ок! ждем следующего запуска.");
            return System.currentTimeMillis() - start + getInterval();
        } catch (IOException | ParseException e) {
            this.conf.log(e);
            this.conf.log("Ощибка ждем 10 сек и снова пробуем.");
            return 10000;
        }
    }

    /** Определение времени до следующего старта.
     * @return Интервал до следующего старта.
     */
    private long getInterval() {
        String confInterval = this.conf.get("interval");
        long interval = confInterval != null && !confInterval.equals("") ? Long.valueOf(confInterval) : 24 * 3600;
        return interval * 1000 - 10;
    }

    /** Парсер записи вакансии.
     * @param element парсируемый элемент.
     * @return true если есть нобходимость проверять следующие записи.
     * @throws ParseException ошибки парсера
     * @throws IOException ошибки парсера
     */
    private boolean parseElement(Element element) throws ParseException, IOException {
        boolean flag = true;
        if (element.getElementsByClass("closedTopic").size() == 0 //пропускаем закрытую тему
                && !element.childNode(0).toString().equals("Важно: ")) {  //и темы модераторов
            Date date = parseDate(element.parent().getElementsByClass("altCol").get(1).text());
            if (date.getTime() > this.lastStart) {
                String text = element.getElementsByTag("a").get(0).text();
                if (searchJava(text)) {
                    parseVacancy(Jsoup.connect(element.
                            getElementsByTag("a").attr("href"))
                            .get().getElementsByClass("msgTable").first());
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Локаль.
     */
    private final Locale locale = new Locale("ru", "RU");
    /**
     * Форматы даты.
     */
    private final SimpleDateFormat
            dateFormat = new SimpleDateFormat("dd MMM yy", this.locale),
            timeFormat = new SimpleDateFormat("dd MMM yy, HH:mm", this.locale);

    /** Проверка строки даты, на необходимость дальнейшей проверки.
     * @param stringTime Дата - время
     * @return true если обработка необходима.
     * @throws ParseException ошибки парсера
     */
    private Date parseDate(String stringTime) throws ParseException {
        stringTime = stringTime.replace("сегодня", this.dateFormat.format(System.currentTimeMillis()));
        stringTime = stringTime.replace("вчера", this.dateFormat.format(new Date(System.currentTimeMillis()
                        - TimeUnit.DAYS.toMillis(1))));
        return this.timeFormat.parse(stringTime);
    }

    /** парсер страницы вакансии.
     * @param element вакансия
     * @throws ParseException ошибки парсера
     */
    private void parseVacancy(Element element) throws ParseException {
        Date date = parseDate(element.getElementsByClass("msgFooter").get(0).ownText());
        if (date.getTime() > this.lastStart) {
            String heading = element.getElementsByClass("messageHeader").get(0).ownText();
            String vacancyDescription = element.getElementsByClass("msgBody").get(1).html();
            this.db.add(heading, vacancyDescription, date);
        }
    }

    /**
     * Ввыражение для поиска объявлений.
     */
    private final Pattern pJava = Pattern.compile("java(?!\\s*script)", Pattern.CASE_INSENSITIVE);

    /** Ищем Java но JavaScript.
     * @param text строка для поиска
     * @return true если удачен
     */
    private boolean searchJava(String text) {
        return this.pJava.matcher(text).find();
    }
}