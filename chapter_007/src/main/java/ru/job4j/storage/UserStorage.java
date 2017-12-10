package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.12.2017
 */
@ThreadSafe
public class UserStorage implements Storage<User> {
    /** хранилище. */
    @GuardedBy("this.storage")
    private final HashMap<Integer, User> storage = new HashMap<>();

    @Override
    public boolean add(User element) {
        boolean success = false;
        if (element != null) {
            int id = element.getId();
            synchronized (this.storage) {
                if (!this.storage.containsKey(id)) {
                    this.storage.put(id, element);
                    success = true;
                }
            }
        }
        return success;
    }

    @Override
    public boolean update(User element) {
        boolean success = false;
        if (element != null) {
            int id = element.getId();
            synchronized (this.storage) {
                if (this.storage.containsKey(id)) {
                    this.storage.replace(id, element);
                    success = true;
                }
            }
        }
        return success;
    }

    @Override
    public boolean delete(User element) {
        boolean success = false;
        if (element != null) {
            synchronized (this.storage) {
                if (this.storage.containsValue(element)) {
                    this.storage.remove(element.getId());
                    success = true;
                }
            }
        }
        return success;
    }

    /** Перевод денег от одного пользователя другому.
     * @param fromId от кого
     * @param toId кому
     * @param amount сколько
     * @return true если перевод успешен.
     */
    boolean transfer(int fromId, int toId, int amount) {
        boolean success = false;
        if (fromId != toId && amount > 0) {
            synchronized (this.storage) {
                User from = this.storage.get(fromId);
                if (from != null) {
                    int fromAmount = from.getAmount();
                    if (fromAmount >= amount) {
                        User to = this.storage.get(toId);
                        if (to != null) {
                            update(new User(fromId, fromAmount - amount));
                            update(new User(toId, to.getAmount() + amount));
                            success = true;
                        }
                    }
                }
            }
        }
        return success;
    }

    /** @return кол-во бабла в хранилище*/
    long getCapacity() {
        long capacity = 0;
        synchronized (this.storage) {
            for (User user : this.storage.values()) {
                capacity += user.getAmount();
            }
        }
        return capacity;
    }

    /** @param id идентификатор
     * @return пользователь */
    User getUser(int id) {
        synchronized (this.storage) {
            return this.storage.get(id);
        }
    }
}
