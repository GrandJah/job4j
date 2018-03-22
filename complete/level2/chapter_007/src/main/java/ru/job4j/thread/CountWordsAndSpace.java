package ru.job4j.thread;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.11.2017
 */
public class CountWordsAndSpace {
    /** Строка великого произведения, для серьезного всестороннего анализа. */
    private String countString = "Жучка во поле гуляла, мяч резиновый нашла!";

    /** Поток считающий слова. */
    class CountWords implements Runnable {
        @Override
        public void run() {
            int count = 0;
            boolean prevChar = false;
            for (char ch : countString.toCharArray()) {
                if (!Character.isLetter(ch) && prevChar) {
                        count++;
                }
                prevChar = Character.isLetter(ch);
            }
            if (prevChar) {
                count++;
            }
            System.out.println("Words:" + count);
        }
    }

    /** Поток считающий пробелы. */
    class CountSpace implements Runnable {
        @Override
        public void run() {
            int count = 0;
            for (char ch : countString.toCharArray()) {
                if (ch == ' ') {
                    count++;
                }
            }
            System.out.println("Space:" + count);
        }
    }


    /**
     * @param args args
     */
    public static void main(String[] args) {

        new CountWordsAndSpace().go(null);
    }

    /**
     * @param countString анализируемая строка
     */
    public void go(String countString) {
        if (countString != null) {
            this.countString = countString;
        }
        new Thread(new CountWords()).start();
        new Thread(new CountSpace()).start();

        System.out.println("end!");
    }

}
