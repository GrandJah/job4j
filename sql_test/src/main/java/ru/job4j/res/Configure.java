package ru.job4j.res;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.12.2017
 */
public class Configure {
    /**
     * Logger.
     */
    private final Logger logger = Logger.getLogger("Logger");
    /**
     * Конфигурация.
     */
    private Properties configure;

    /**
     * @param config путь к пользовательской конфигурации
     */
    public Configure(String config) {
        Properties prop = new Properties();
        try {
            try (InputStream input = getClass().getResourceAsStream("/default.config")) {
                prop.load(input);
            }
            this.configure = new Properties(prop);
            if (config != null) {
                try (InputStream input = new FileInputStream(config)) {
                    this.configure.load(input);
                }
            }
        } catch (FileNotFoundException e) {
            log("не обнаружен файл настроек, используются настройки по умолчанию.");
        } catch (IOException e) {
            log(e.getMessage());
        } finally {
            if (this.configure == null) {
                this.configure = new Properties();
            }
        }
    }

    /** Взять значение по ключу.
     * @param property ключ
     * @return значение ключа
     */
    public String get(String property) {
        return this.configure.getProperty(property);
    }

    /**
     * @param message сообщение
     */
    public void log(String message) {
        this.logger.info(message);
    }

    /**
     * @param e исключение
     */
    public void log(Exception e) {
        this.logger.info(e.getMessage());
        e.printStackTrace();
    }
}
