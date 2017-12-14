package ru.job4j.parallelsearch;

import ru.job4j.blockedqueue.BlockQueue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.12.2017
 */
public class ParallelSearch {
    /** расширения файлов в которых нужно делать поиск. */
    private final String[] exts;

    /**заданных текст для поиска. */
    private final String text;

    /** Очередь на обработку. */
    private BlockQueue<Future<String>> queue = new BlockQueue<>(100);

    /** Результат поиска. */
    private final List<String> result;

    /** Готовность результатов поиска. */
    private boolean isReady = false;

    /**@return isReady готовность. */
    public boolean isReady() {
        return this.isReady;
    }

    /**
     * @param root путь до папки откуда надо осуществлять поиск.
     * @param text заданных текст.
     * @param exts расширения файлов в которых нужно делать поиск.
     */
    public ParallelSearch(String root, String text, List<String> exts) {
        this.text = text;
        this.exts = new String[exts.size()];
        result = new LinkedList<>();
        exts.toArray(this.exts);
        search(root);

    }

    /** Потоки обработки. */
    private ExecutorService executorService;

    /** Поиск текста.
     * @param root корень поиска
     */
    private void search(String root) {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Thread creator = new Thread(() -> searchRecursion(new File(root)));
        creator.start();

        Thread getResult = new Thread(() -> {
            while (true) {
                try {
                    Future<String> task = this.queue.take();
                    String searchResult = task.get();
                    if (searchResult != null) {
                        result.add(searchResult);
                    }
                } catch (InterruptedException e) {
                    break;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        getResult.start();

        new Thread(() -> {
            try {
                creator.join();
                while (this.queue.size() > 0) {
                    Thread.sleep(10);
                }
                getResult.interrupt();
                getResult.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.isReady = true;
            synchronized (this.result) {
                this.result.notify();
            }

            this.executorService.shutdown();
        }).start();
    }

    /** Рекурсивный поиск.
     * @param fileSearch файл поиска
     */
    private void searchRecursion(File fileSearch) {
        if (fileSearch.isDirectory()) {
            File[] files = fileSearch.listFiles();
            if (files == null) { // Папка есть а файлов нет.
                System.out.println(fileSearch + " - ошибка доступа к директории.");
            } else {
                for (File file : files) {
                    searchRecursion(file);
                }
            }
        } else {
            if (checkFileExtension(fileSearch)) {
                try {
                    this.queue.put(this.executorService.submit(new FileParser(fileSearch)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Проверка имеется ли у файла нужное расширение.
     * @param file файл поиска
     * @return true если является
     */
    private boolean checkFileExtension(File file) {
        String[] sp = file.getName().split("\\.");
        String extCheck = sp[sp.length - 1];
        boolean check = false;
        for (String ext:exts) {
            if (sp.length == 1 && ext.equals("") || ext.equals(extCheck)) {
                check = true;
                break;
            }
        }
        return check;
    }

    /** Результаты поиска.
     *  Блокируется до появления результата.
     *  @return результат поиска.
     */
    public List<String> result() {
        while (!this.isReady) {
            synchronized (this.result) {
                try {
                    this.result.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.result;
    }

    /** Задача на поиск в файле. */
    private class FileParser implements Callable<String> {
        /** Проверяемый файл. */
        private final File file;

        /**@param file файл где осущевстояется поиск */
        FileParser(File file) {
            this.file = file;
        }

        @Override
        public String call() throws Exception {
            String fileName = null;
            try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
                while (reader.ready()) {
                    if (reader.readLine().contains(text)) {
                        fileName = this.file.getAbsolutePath();
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            return fileName;
        }
    }

    /** @param args args */
    public static void main(String[] args) {
        List<String> exts = new LinkedList<>();
        Collections.addAll(exts, "txt", "java", "");
        ParallelSearch search = new ParallelSearch("c:\\windows", "127.0.0.1", exts);
        System.out.println("Поиск создан");
        System.out.println(search.isReady());
        List<String> result = search.result();
        System.out.println("файлы в которых содержится искомый текст:");
        for (String string:result) {
            System.out.println(string);
        }
    }
}
