#include "CritterEspecial.h"
// ========== CRITTER ESPECIAL ==========
CritterEspecial::CritterEspecial(const string& name)
    : Critter(name, 20, 20), m_habilitat("Super Salt") {
}

void CritterEspecial::usarHabilitat() {
    cout << GetName() << " usa " << m_habilitat << "! \n";
    cout << "El avorriment baixa sobtadament!\n";
    SetBoredom(GetBoredom() - 40);
    if (GetBoredom() < 0) SetBoredom(0);
}

void CritterEspecial::showStatus() const {
    Critter::showStatus();
    cout << " Habilitat: " << m_habilitat << "\n";
    cout << "=======================================\n";
}

void CritterEspecial::Greet() const {
    cout << " Hola! Soc " << GetName() << " i soc especial! \n";
}