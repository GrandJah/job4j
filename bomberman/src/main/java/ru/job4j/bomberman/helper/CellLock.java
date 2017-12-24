package ru.job4j.bomberman.helper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public class CellLock {
    /**
     * Блокировка.
     */
    private ReentrantLock locker = new ReentrantLock();

    /**
     * объект владеющий блокировкой.
     */
    private Entity entity;

    /** взять блокировку.
     * @param entity объект просящий блокировку.
     */
    public void lock(Entity entity) {
        this.locker.lock();
        this.entity = entity;
    }

    /** Запрос блокировки без ожидания.
     * @param entity объект просящий блокировку.
     * @return объект владеющий блокировкой
     */
    public Entity tryLock(Entity entity) {
        if (this.locker.tryLock()) {
            this.entity = entity;
        }
        return this.entity;
    }

    /** Запрос блокировки с ожиданием в 0.5 сек.
     * @param entity объект просящий блокировку.
     * @return объект владеющий блокировкой
     */
    public Entity waitLock(Entity entity) {
        try {
            if (this.locker.tryLock(500, TimeUnit.MILLISECONDS)) {
                this.entity = entity;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.entity;
    }

    /**
     * разблокировка ячейки.
     */
    public void unlock() {
        if (this.locker.isHeldByCurrentThread()) {
            this.entity = null;
            this.locker.unlock();
        }
    }

    /**
     * @return маркер
     */
    public char getTypeLetter() {
        char letter = ' ';
        if (this.locker.isLocked()) {
            switch (this.entity.type()) {
                case BomberMan: letter = 'O'; break;
                case Monster: letter = '+'; break;
                case Wall: letter = 'X'; break;
                default:
            }
        }
        return letter;
    }
}
