#pragma once
#include "Character.h"
class Vistlou : public Character {
public:
    Vistlou(float x, float y);

    void handleInput() override;
};
