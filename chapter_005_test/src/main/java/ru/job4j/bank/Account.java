package ru.job4j.bank;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class Account {
    /**
     * Сумма на счете.
     */
    private double values;
    /**
     * Реквизиты счета.
     */
    private String requisites;

    /**
     * @param values деньги на счете
     * @param requisites реквизиты
     */
    public Account(double values, String requisites) {
        this.values = values;
        this.requisites = requisites;
    }

    /**
     * Getter.
     * @return values values
     */
    public double getValues() {
        return this.values;
    }

    /**
     * Getter.
     * @return requisites requisites
     */
    public String getRequisites() {
        return this.requisites;
    }

    /** Перевод денег.
     * @param destination счет получателя
     * @param amount сумма перевода
     * @return успех операции
     */
    public boolean transfer(Account destination, double amount) {
        boolean success = false;
        if (amount > 0 && amount < this.values && destination != null) {
            success = true;
            this.values -= amount;
            destination.values += amount;
        }
        return success;
    }
}
