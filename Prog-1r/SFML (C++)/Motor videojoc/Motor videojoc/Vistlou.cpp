#include "Vistlou.h"
Vistlou::Vistlou(float x, float y) : Character(x, y, 250.0f, -450.0f) {
    shape.setFillColor(Color::Blue);
}

void Vistlou::handleInput() {
    // Controls: WASD
    velocity.x = 1;
    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Key::Left)) {
        velocity.x = -speed;
    }
    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Key::Right)) {
        velocity.x = speed;
    }

    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Key::Up) && !isJumping) {
        velocity.y = jumpForce;
        isJumping = true;
    }
}