#pragma once
#include "Granja.h"
#include <vector>

class CritterEspecial : public Critter {
private:
    string m_habilitat;

public:
    CritterEspecial(const string& name);
    void usarHabilitat();
    void showStatus() const override;
    void Greet() const override;
};

