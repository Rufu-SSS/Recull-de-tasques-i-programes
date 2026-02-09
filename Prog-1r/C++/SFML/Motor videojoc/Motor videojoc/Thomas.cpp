#include "Thomas.h"
Thomas::Thomas(float x, float y) : Character(x, y, 250.0f, -450.0f) {
    shape.setFillColor(Color::Green);
}

void Thomas::handleInput() {
    // Controls: WASD
    velocity.x = 0;
    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Key::A)) {
        velocity.x = -speed;
    }
    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Key::D)) {
        velocity.x = speed;
    }

    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Key::W) && !isJumping) {
        velocity.y = jumpForce;
        isJumping = true;
    }
}