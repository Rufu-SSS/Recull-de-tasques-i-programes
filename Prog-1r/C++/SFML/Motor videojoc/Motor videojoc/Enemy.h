#pragma once
#include "Character.h"

// Enemic controlat per la IA
class Enemy : public Character {
private:
    float patrolLeft;
    float patrolRight;
    int direction; // -1 esquerra, 1 dreta

public:
    Enemy(float x, float y, float spd, float jmp,
        float leftLimit, float rightLimit);

    void handleInput() override;  // IA de moviment
};
