#pragma once
#include <SFML/Graphics.hpp>    
#include <vector>
#include <fstream>
#include <iostream>
#include <string>
#include "GameObject.h"

// Classe per a plataformes (també hereta de GameObject)
class Platform : public GameObject {
public:
    Platform(float x, float y, float width, float height);
    Platform(float x, float y);
    void update(float dt) override;
};

// Classe per gestionar el mapa complet de plataformes
class PlatformMap {
private:
    std::vector<Platform> platforms;
    const int TILE_SIZE = 40;
    Vector2f playerStartPosition;
    Platform goal;
public:
    PlatformMap();
    // Carrega el mapa des d'un fitxer
    bool loadFromFile(const std::string& filename, RenderWindow& window);
    // Getters
    const std::vector<Platform>& getPlatforms() const;
    Vector2f getPlayerStartPosition() const;
    Platform getGoal();
    // Dibuixa totes les plataformes
    void draw(RenderWindow& window);
    // Actualitza totes les plataformes (per si hi ha plataformes mòbils)
    void update(float dt);
};
