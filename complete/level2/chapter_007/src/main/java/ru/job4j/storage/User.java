package ru.job4j.storage;

/**  Пользователь с баблом на счете.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.12.2017
 */
public class User {
    /** идентификатор. */
    private final int id;
    /** Сумма на счете. */
    private int amount;

    /** @param id идентификатор
     *  @param amount счет пользователя
     */
    User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**@return id*/
    int getId() {
        return this.id;
    }

    /** @return amount*/
    int getAmount() {
        return this.amount;
    }
}
