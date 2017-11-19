package ru.job4j.thread;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.11.2017
 */
public class TimeOutProgram {

    /** Поток таймера. */
    static class Time implements Runnable {
        /** тайм-аут в миллисекндах. */
        private long timeOut;
        /** управляемый поток.*/
        private Thread thread;

        /**
         * @param timeOutMillis тайм-аут в миллисекндах.
         * @param thread управляемый поток.
         */
        Time(long timeOutMillis, Thread thread) {
            this.timeOut = System.currentTimeMillis() + timeOutMillis;
            this.thread = thread;
            thread.start();
        }

        @Override
        public void run() {
            System.out.println("timer start");
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!thread.isAlive()) {
                    System.out.println("Таймер остановлен");
                    break;
                }
                if (this.timeOut < System.currentTimeMillis()) {
                    System.out.println("время истекло");
                    thread.interrupt();
                    break;
                }
            }
        }
    }

    /** Поток подсчета символов. */
    static class CountChar implements Runnable {
        /** Строка для подсчета символов. */
        private String string;

        /**
         * @param string строка для подсчета символов
         */
        CountChar(String string) {
            this.string = string == null ? "" : string;
        }

        @Override
        public void run() {
            int count = 0;
            for (char ch : this.string.toCharArray()) {
                count++;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("поток прерван");
                    break;
                }
            }
            System.out.println("насчитано символов: " + count);
        }
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("Start!");
        String string = "Великолепная срока для подсчета символов.";
        new Thread(new Time(500, new Thread(new CountChar(string)))).start();
        System.out.println("End!");
    }
}

