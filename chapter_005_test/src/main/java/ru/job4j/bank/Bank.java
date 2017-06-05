package ru.job4j.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class Bank {
    /**
     * Базза данных пользователей.
     */
    private TreeMap<User, List<Account>> accounts = new TreeMap<>();

    /** добавление пользователя.
     * @param user Пользователь.
     */
    public void addUser(User user) {
        this.accounts.put(user, new ArrayList<>());
    }

    /** удаление пользователя.
     * @param user Пользователь.
     */
    public void deleteUser(User user) {
        this.accounts.remove(user);
    }

    /** добавить счёт пользователю.
     * @param user Пользователь.
     * @param account Добавляемый чет.
     */
    public void addAccountToUser(User user, Account account) {
        this.accounts.get(user).add(account);
    }

    /** удалить один счёт пользователя.
     * @param user Пользователь.
     * @param account удаляемый счет.
     */
    public void deleteAccountFromUser(User user, Account account) {
        this.accounts.get(user).remove(account);
    }

    /** получить список счетов для пользователя.
     * @param user Пользователь.
     * @return Счета пользователя.
     */
    public List<Account> getUserAccounts(User user) {
        return this.accounts.get(user);
    }

    /** возращает актуальную ссылку на счетах в БД банка.
     * @param user Клиент банка
     * @param account Счет клиента а нужны только реквизиты.
     * @return Фактический счет клиента из БД
     */
    private Account getActualAccount(User user, Account account) {
        List<Account> list = this.accounts.get(user);
        return list.get(list.indexOf(account));
    }

    /** перечисления денег с одного счёта на другой счёт.
     * @param srcUser Плательщик
     * @param srcAccount Счет оплаты
     * @param dstUser Получатель
     * @param dstAccount Счет получения
     * @param amount сумма перевода
     * @return Успех перевода.
     */
    public boolean transferMoney(User srcUser, Account srcAccount,
                                  User dstUser, Account dstAccount, double amount) {
        return this.accounts.get(srcUser).contains(srcAccount)
                && this.accounts.get(dstUser).contains(dstAccount)
                && getActualAccount(srcUser, srcAccount).transfer(
                        getActualAccount(dstUser, dstAccount), amount);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bank{");
        sb.append("accounts=").append(accounts);
        sb.append('}');
        return sb.toString();
    }
}
