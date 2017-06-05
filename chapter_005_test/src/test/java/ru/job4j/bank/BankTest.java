package ru.job4j.bank;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;


/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class BankTest {
    /**
     * @return Банк заполненный клиентами.
     */
    private Bank fillBankUser() {
        Bank bank = new Bank();
        bank.addUser(new User("Иванов", 345678212));
        bank.addUser(new User("Петров", 342328212));
        bank.addUser(new User("Сидоров", 122678212));
        bank.addUser(new User("Ванькин", 988678212));
        bank.addUser(new User("Встанькин", 235678332));
        return bank;
    }
    /**
     * Test method.
     */
    @Test
    public void whenAddUserThenOk() {
        assertThat(fillBankUser().toString(), is("Bank{accounts={"
                + "User{name='Ванькин', passport=988678212}=[], "
                + "User{name='Встанькин', passport=235678332}=[], "
                + "User{name='Иванов', passport=345678212}=[], "
                + "User{name='Петров', passport=342328212}=[], "
                + "User{name='Сидоров', passport=122678212}=[]"
                + "}}"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenDeleteUserThenOk() {
        Bank bank = fillBankUser();
        bank.deleteUser(new User("Сидоров", 122678212));
        bank.deleteUser(new User("Встанькин", 235678332));
        assertThat(bank.toString(), is("Bank{accounts={"
                + "User{name='Ванькин', passport=988678212}=[], "
                + "User{name='Иванов', passport=345678212}=[], "
                + "User{name='Петров', passport=342328212}=[]"
                + "}}"));
    }

    /**
     * Test method.
     */
    @Test
    public void addAccountToUser() {
        Bank bank = fillBankUser();
        bank.addAccountToUser(new User("Сидоров", 122678212), new Account(0, "seller"));
        assertThat(bank.toString(), is("Bank{accounts={"
                + "User{name='Ванькин', passport=988678212}=[], "
                + "User{name='Встанькин', passport=235678332}=[], "
                + "User{name='Иванов', passport=345678212}=[], "
                + "User{name='Петров', passport=342328212}=[], "
                + "User{name='Сидоров', passport=122678212}=["
                + "Account{values=0.0, requisites='seller'}"
                + "]}}"));
    }

    /**
     * Test method.
     */
    @Test
    public void deleteAccountFromUser() {
        Bank bank = fillBankUser();
        User user = new User("Сидоров", 122678212);
        Account account = new Account(0, "seller");
        bank.addAccountToUser(user, account);
        bank.deleteAccountFromUser(user, account);
        assertThat(bank.toString(), is("Bank{accounts={"
                + "User{name='Ванькин', passport=988678212}=[], "
                + "User{name='Встанькин', passport=235678332}=[], "
                + "User{name='Иванов', passport=345678212}=[], "
                + "User{name='Петров', passport=342328212}=[], "
                + "User{name='Сидоров', passport=122678212}=[]"
                + "}}"));
    }

    /**
     * Test method.
     */
    @Test
    public void getUserAccounts() {
        Bank bank = fillBankUser();
        User user = new User("Сидоров", 122678212);
        Account account = new Account(0, "seller");
        bank.addAccountToUser(user, account);
        assertThat(bank.getUserAccounts(user).toString(), is("[Account{values=0.0, requisites='seller'}]"));

    }

    /**
     * Test method.
     */
    @Test
    public void transferMoney() {
        Bank bank = fillBankUser();
        User user1 = new User("Сидоров", 122678212);
        User user2 = new User("Встанькин", 235678332);
        Account account1 = new Account(100, "seller");
        Account account2 = new Account(100, "bayer");
        bank.addAccountToUser(user1, account1);
        bank.addAccountToUser(user2, account2);
        bank.transferMoney(user2, account2, user1, account1, 15);
        assertThat(account1.getValues(), closeTo(115, 0.01));
        assertThat(account2.getValues(), closeTo(85, 0.01));

    }

    /**
     * Test method.
     */
    @Test
    public void whenBadAccountForTransferMoneyThenNoOperation() {
        Bank bank = fillBankUser();
        User user1 = new User("Сидоров", 122678212);
        User user2 = new User("Встанькин", 235678332);
        Account account1 = new Account(100, "seller");
        Account account2 = new Account(100, "bayer");
        Account badAccount = new Account(1000, "bayer");
        bank.addAccountToUser(user1, account1);
        bank.addAccountToUser(user2, account2);
        assertThat(bank.transferMoney(user2, badAccount, user1, account1, 200), is(false));
        assertThat(account1.getValues(), closeTo(100, 0.01));
        assertThat(account2.getValues(), closeTo(100, 0.01));

    }

}