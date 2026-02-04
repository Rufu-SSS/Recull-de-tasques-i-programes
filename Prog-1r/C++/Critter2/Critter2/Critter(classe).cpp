#include "Critter(classe).h"

// CORRECCIÓ: Afegir "const" i "&" per coincidir amb el header
Critter::Critter(const string& name, int hunger, int boredom)
    : m_name(name), m_hunger(hunger), m_boredom(boredom), m_age(0), m_state("Neutral") {
    actualitzarEstat();
}

void Critter::Greet() const {
    cout << "Hola! Soc " << m_name << "!" << endl;
}

int Critter::GetHunger() const {
    return m_hunger;
}

int Critter::GetBoredom() const {
    return m_boredom;
}

const string& Critter::GetName() const {
    return m_name;
}

int Critter::GetEstatAnimic() const {
    return getMood();
}

void Critter::SetHunger(int hunger) {
    m_hunger = hunger;
}

void Critter::SetBoredom(int boredom) {
    m_boredom = boredom;
}

void Critter::menjar() {
    cout << m_name << " menja i es sent millor!" << endl;
    m_hunger -= 20;
    if (m_hunger < 0) m_hunger = 0;
    actualitzarEstat();
}

void Critter::jugar() {
    cout << m_name << " juga i s'ho passa be!" << endl;
    m_boredom -= 30;
    if (m_boredom < 0) m_boredom = 0;
    actualitzarEstat();
}

void Critter::escoltar() {
    cout << m_name << " estic escoltant atentament." << endl;
    m_boredom -= 15;
    if (m_boredom < 0) m_boredom = 0; 
    actualitzarEstat();
}

void Critter::actualitzarEstat() {
    if (m_hunger > 70) m_state = "Amb gana";
    else if (m_boredom > 70) m_state = "Moolt avorrit";
    else if (m_hunger < 30 && m_boredom < 30) m_state = "Content";
    else m_state = "Neutral";
}
void Critter::tempsPassa(int tempsMinuts=1) {
    m_hunger += tempsMinuts * 2;
    m_boredom += tempsMinuts * 3;
    if (m_hunger > 100) m_hunger = 100;
    if (m_boredom > 100) m_boredom = 100;
    m_age += tempsMinuts;
    actualitzarEstat();
}

int Critter::getMood() const {
    int total = m_hunger + m_boredom;
    if (total < 20) return 0;
    if (total < 50) return 1;
    return 2;
}

void Critter::showStatus() const {
    cout << "========== ESTAT DEL CRITTER ==========\n";
    cout << "Nom: " << m_name << endl;
    cout << "Gana: [" << string(m_hunger / 10, '/') << string(10 - m_hunger / 10, '.') << "] "
        << m_hunger << "/100\n";
    cout << "Avorriment: [" << string(m_boredom / 10, '/') << string(10 - m_boredom / 10, '.') << "] "
        << m_boredom << "/100\n";
    cout << "Estat Animic: " << m_state << endl;
    cout << "Edat: " << m_age << " dies\n";
    cout << "=======================================\n";
}

ostream& operator<<(ostream& os, const Critter& aCritter) {
    os << "Critter Object - ";
    os << "m_name: " << aCritter.m_name;
    return os;
}


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