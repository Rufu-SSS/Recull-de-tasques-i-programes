#include "Enemy.h"

Enemy::Enemy(float x, float y, float spd, float jmp,
    float leftLimit, float rightLimit)
    : Character(x, y, spd, jmp),
    patrolLeft(leftLimit),
    patrolRight(rightLimit),
    direction(1) {
}

void Enemy::handleInput() {
    // Moviment automàtic (patrulla)
    velocity.x = speed * direction;

    if (position.x <= patrolLeft) {
        direction = 1;
    }
    else if (position.x >= patrolRight) {
        direction = -1;
    }
}
