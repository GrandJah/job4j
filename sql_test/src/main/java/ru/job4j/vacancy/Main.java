package ru.job4j.vacancy;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 23.12.2017
 */
public class Main {
    /**
     * @param args args
     */
    public static void main(String[] args) {
        final Main main = new Main();
        main.start("src/test/config/test.config");

    }

    /**
     * Цикл системы с интервалом.
     * @param config файл конфигурации
     */
    private void start(String config) {
        while (true) {
            try {
                synchronized (this) {
                    wait(run(config));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /** Запуск системы.
     * @return время ожидания до следующего запуска.
     * @param config файл конфигурации
     */
    private long run(String config) {
        StartSystem system = new StartSystem(config);
        return system.start();
    }
}
