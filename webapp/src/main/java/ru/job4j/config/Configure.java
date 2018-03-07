package ru.job4j.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Igor Kovalkov
 */
public class Configure {
    /** log. */
    private static final Logger LOG = Logger.getLogger(Configure.class.getName());

    /**
     * Конфигурация.
     */
    private Properties configure;

    /**
     * cash properties.
     */
    private static final Map<String, Properties> CASH = new HashMap<>();

    /**
     * @param config путь к пользовательской конфигурации
     */
    public Configure(String config) {
        if (!CASH.containsKey(config)) {
            this.configure = new Properties();
            try (InputStream input = getClass().getResourceAsStream("/default.config")) {
                this.configure.load(input);
            } catch (IOException e) {
                LOG.info(e.toString());
            }
            if (config != null) {
                boolean flag = true;
                Exception ex = null;
                try (InputStream input = getClass().getResourceAsStream(config)) {
                    this.configure.load(input);
                } catch (Exception e) {
                    flag = false;
                    ex = e;
                }
                try (InputStream input = new FileInputStream(config)) {
                    this.configure.load(input);
                } catch (Exception e) {
                    flag = !flag;
                    ex = e;
                }
                if (flag) {
                    assert ex != null;
                    LOG.info(ex.getMessage());
                }
            }
            CASH.put(config, this.configure);
        } else {
            this.configure = CASH.get(config);
        }
    }

    /** Взять значение по ключу.
     * @param property ключ
     *
     * @return значение ключа
     */
    public String get(String property) {
        return this.configure.getProperty(property);
    }

    /** Resources o string.
     * @param urlResources resources path
     * @return string resource
     * @throws IOException IOException
     */
    public static String resToString(String urlResources) {
        final char[] buffer = new char[1024];
        final StringBuilder out = new StringBuilder();
        Reader in = null;
        try {
            in = new InputStreamReader(Class.class.getResourceAsStream(urlResources), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (;;) {
            int rsz = 0;
            try {
                rsz = in.read(buffer, 0, buffer.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (rsz < 0) {
                break;
            }
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}
