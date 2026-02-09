#pragma once
#include "Character.h"
class Thomas : public Character {
public:
    Thomas(float x, float y);

    void handleInput() override;
};
