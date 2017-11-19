package ru.job4j.thread;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.11.2017
 */
public class WaitCalculations {

    /**
     * Поток для долгих вычислений.
     */
    static class Calculations implements Runnable {

        @Override
        public void run() {
            System.out.println("Start calculations!");
            try {
                Thread.sleep((long) (Math.random() * 10000)); // Типа что-то делаем.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End calculations!");
        }
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("Info about program!");
        Thread calculation = new Thread(new Calculations());
        calculation.start();
        try {
            calculation.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The end!");

    }
}
