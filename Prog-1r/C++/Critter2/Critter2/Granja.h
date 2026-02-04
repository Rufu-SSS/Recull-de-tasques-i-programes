#pragma once
#include <vector>
#include <string>
#include <iostream>
#include "Critter(classe).h"
#include "CritterEspecial.h"

class CritterEspecial;

class Granja {
private:
    vector<Critter*> critters;

public:
    Granja();
    void afegirCritter(Critter* c);
    void llistarCritters() const;
    Critter* buscarCritterPerNom(const string& m_name);
    vector<Critter*> buscarCrittersGana();
    vector<CritterEspecial*> buscarSpecialCritters() const;
    void tempsPassaPerTots(int tempsMinutsGranja);
    void alimentarFamolics();  
    void entretenir();  
    int comptarCritters() const;  
    ~Granja();
};