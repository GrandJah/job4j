package ru.job4j.bomberman.hero;

import ru.job4j.bomberman.helper.Cell;
import ru.job4j.bomberman.helper.Entity;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 16.12.2017
 */
public class Hero extends Entity {
    /**
     * Направления перехода.
     */
    enum Direction {
        /**
         * Возможные направления.
         */
        Up, Down, Left, Right;

        /**
         * @return случайное направление.
         */
        public static Direction random() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    /**
     * @param type тип героя
     */
    Hero(Entity.Type type) {
        super(type);
    }

    /** Переход на новую клетку.
     * @param source источник
     * @param direction направление перехода.
     * @return Герой на новом месте
     */
    public Cell goNewCell(Cell source, Direction direction) {
        int x = source.getX();
        int y = source.getY();
        switch (direction) {
            case Up:
                y--;
                break;
            case Down:
                y++;
                break;
            case Left:
                x--;
                break;
            case Right:
                x++;
                break;
            default:
                break;
        }
        return new Cell(x, y);
    }
}
