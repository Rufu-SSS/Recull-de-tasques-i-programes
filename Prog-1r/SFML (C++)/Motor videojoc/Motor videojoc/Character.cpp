#include "Character.h"
Character::Character(float x, float y, float spd, float jmp)
    : GameObject(x, y, 40, 60) {
    velocity = Vector2f(0, 0);
    isJumping = false;
    speed = spd;
    jumpForce = jmp;
}

void Character::update(float dt) {
    // Aplica gravetat
    velocity.y += GRAVITY * dt;

    // Actualitza posició
    position.x += velocity.x * dt;
    position.y += velocity.y * dt;

    // Col·lisió amb el terra (simplificat)
    if (position.y > 500) {
        position.y = 500;
        velocity.y = 0;
        isJumping = false;
    }

    // Límits laterals
    if (position.x < 0) position.x = 0;
    if (position.x > 760) position.x = 760;

    shape.setPosition(position);
}

void Character::setPosition(float x, float y) {
    position = Vector2f(x, y);
    shape.setPosition(position);
}

void Character::stopFalling(float groundY) {
    position.y = groundY;
    velocity.y = 0;
    isJumping = false;
    shape.setPosition(position);
}
