package ru.job4j.parallelsearch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.12.2017
 */
@ThreadSafe
public class ParallelSearch {
    /** список всех найденных файлов. */
    @GuardedBy("this.result")
    private final List<String> result;

    /** расширения файлов в которых нужно делать поиск. */
    private final String[] exts;

    /**заданных текст для поиска. */
    private final String text;

    /** Путь корневого каталога поиска. */
    private final String root;

    /**
     * @param root путь до папки откуда надо осуществлять поиск.
     * @param text заданных текст.
     * @param exts расширения файлов в которых нужно делать поиск.
     */
    public ParallelSearch(String root, String text, List<String> exts) {
        this.text = text;
        this.exts = new String[exts.size()];
        exts.toArray(this.exts);
        this.result = new ArrayList<>();
        this.root = root;
    }

    /** Рекурсивный поиск.
     * @param filesearch файл поиска
     */
    private void searchRecursion(File filesearch) {
        if (filesearch.isDirectory()) {
            ArrayList<Thread> threads = new ArrayList<>();
            for (File file : filesearch.listFiles()) {
                threads.add(new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        searchRecursion(file);
                    }
                });
            }
            for (Thread thread:threads) {
                thread.start();
            }
            for (Thread thread:threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else {
            if (checkFileExtension(filesearch)) {
                fileParser(filesearch);
            }
        }
    }

    /** Старт поиска. */
    public void search() {
        searchRecursion(new File(root));
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
            if (ext.equals(extCheck)) {
                check = true;
                break;
            }
        }
        return check;
    }


    /** Поиск строки в файле.
     * @param file файл поиска
     */
    private void fileParser(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (reader != null) {
                while (reader.ready()) {
                    if (reader.readLine().contains(this.text)) {
                        synchronized (this.result) {
                            this.result.add(file.getAbsolutePath());
                            break;
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** @return резудьтат поиска. */
    public List<String> result() {
        synchronized (this.result) {
            return this.result;
        }
    }


    /** @param args */
    public static void main(String[] args) {
        List<String> exts = new ArrayList<>();
        Collections.addAll(exts, "txt", "java");
        ParallelSearch search = new ParallelSearch(".\\",
                "e408e3646b9296204a328fbf56d86071",
                exts);
        search.search();
        List<String> result = search.result();
        for (String string:result) {
            System.out.println(string);
        }
    }
}
