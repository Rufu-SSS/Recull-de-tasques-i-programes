#include "GameObject.h"
GameObject::GameObject(float x, float y, float width, float height) {
    position = Vector2f(x, y);
    shape.setSize(Vector2f(width, height));
    shape.setPosition(position);
}
GameObject::GameObject(const string& name):name(name){}

void GameObject::draw(RenderWindow& window) {
    window.draw(shape);
}

FloatRect GameObject::getBounds() {
    return shape.getGlobalBounds();
}

Vector2f GameObject::getPosition() {
    return position;
}
void GameObject::setPosition(float x, float y) {
    position = Vector2f(x, y);
    shape.setPosition(position);
}