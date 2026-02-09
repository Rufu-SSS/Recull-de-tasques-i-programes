#pragma once
#include <iostream>
#include <vector>
#include <string>
#include <cstdlib>
#include <limits>
using namespace std;

class Critter {
private:
    int m_hunger;
    int m_boredom;
    int m_age;
    string m_name;
    string m_state;
    int getMood() const;

public:
    Critter(const string& name, int hunger = 0, int boredom = 0);
    Critter operator+(const Critter& duplicat) const {
        string nomDuplicat = m_name + "-" + duplicat.m_name;
        int m_hungerInicial = (m_hunger + duplicat.m_hunger) / 2;
        int m_boredomInicial = (m_boredom + duplicat.m_boredom) / 2;
        return Critter(nomDuplicat, m_hungerInicial, m_boredomInicial);
    }

    virtual void Greet() const;
    void menjar();
    void jugar();
    void escoltar();
    void actualitzarEstat();
    void tempsPassa(int tempsMinuts);
    virtual void showStatus() const;
    virtual ~Critter() = default;

    const string& GetName() const;
    int GetHunger() const;
    int GetBoredom() const;
    int GetEstatAnimic() const;

    void SetHunger(int hunger);
    void SetBoredom(int boredom);

    friend ostream& operator<<(ostream& os, const Critter& c);
};


/*
   Jugar, menjar i escoltar
	  · aquestes accions permeten mantenir a la criatura de bon humor i sense gana.
	  · l'estat anímic ha d'anar lligat als seus nivells de gana i avorriment (content a avorrit)
   showStatus ha de mostrar més info
   sobrecarregar l'operador "+" per ajuntar dues mascotes
   Funció que simuli el pas del temps (augmentar lentament els valors de gana i avorriment dels critters)
   Subclasse filla que permeti generar nous critters
   Classe granja on guardem els critters
   Guardarem pràcticament tot en vectors per tal de buscar-hi a dins més endavant
*/