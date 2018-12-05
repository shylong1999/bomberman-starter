package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIRandom extends AI {


    Bomber _bomber;
    Enemy _e;

    public AIRandom(Bomber bomber, Enemy e) {
        _bomber = bomber;
        _e = e;
    }
    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}
