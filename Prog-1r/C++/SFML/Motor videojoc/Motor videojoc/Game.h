#pragma once
#include <SFML/Graphics.hpp>
#include "Thomas.h"
#include "Platform.h"
#include "Vistlou.h"
#include "Enemy.h"

using namespace std;
using namespace sf;

// Classe principal del joc
class Game {
private: //dades
    RenderWindow window;
    Thomas player;
    Vistlou player2;
    //Enemy enemy;
    Clock clock;
    sf::Font font;
    Text instructionsText;
    PlatformMap levelLoader;

public://metodes
    Game();
    void run();

private: //metodes
    void handleEvents();
    void update(float dt);
    void draw();
    void checkCollisions(Character& character);
    void checkGoal(Platform goal);


};
