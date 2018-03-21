package ru.job4j.bomberman.hero;

import ru.job4j.bomberman.helper.Board;
import ru.job4j.bomberman.helper.Cell;
import ru.job4j.bomberman.helper.Entity;


/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public class Monster extends AutoStepHero {
    /**
     * @param cell старотовая координата
     * @param board  игровое поле
     */
    public Monster(Cell cell, Board board) {
        super(cell, board, new MonsterHero());
    }
}

/**
 * Monster.
 */
class MonsterHero extends Hero {
    /**
     * Default.
     */
    MonsterHero() {
        super(Type.Monster);
    }

    @Override
    public void signal(Entity entity) {
        if (entity.type() == Type.BomberMan) {
            entity.signal(this);
        }
    }
}
