#include "Granja.h"

Granja::Granja() {}

void Granja::afegirCritter(Critter* c) {
    critters.push_back(c);
}

void Granja::llistarCritters() const {
    if (critters.empty()) {
        cout << "\n La granja es troba buida! Crea algun Critter primer.\n";
        return;
    }

    for (size_t i = 0; i < critters.size(); i++) {
        cout << "\n|==================================|\n";
        cout << "| Critter #" << (i + 1) << "                       |\n";
        cout << "|==================================|\n";
        critters[i]->showStatus();
    }
}

Critter* Granja::buscarCritterPerNom(const string& nom) {
    for (auto& c : critters) {
        if (c->GetName() == nom)
            return c;
    }
    return nullptr;
}

vector<Critter*> Granja::buscarCrittersGana() {
    vector<Critter*> ambGana;
    for (auto& c : critters) {
        if (c->GetHunger() > 70) ambGana.push_back(c);
    }
    return ambGana;
}

vector<CritterEspecial*> Granja::buscarSpecialCritters() const {
    vector<CritterEspecial*> especials;
    for (auto& c : critters) {
        if (auto esp = dynamic_cast<CritterEspecial*>(c)) {
            especials.push_back(esp);
        }
    }
    return especials;
}

void Granja::tempsPassaPerTots(int tempsMinutsGranja) {
    for (auto& c : critters) {
        c->tempsPassa(tempsMinutsGranja);
    }
}

void Granja::alimentarFamolics() {
    auto famolics = buscarCrittersGana();
    if (famolics.empty()) {
        cout << " Cap Critter vol menjar encara!\n";
        return;
    }

    cout << " Alimentant " << famolics.size() << " Critter(s)...\n";
    for (auto& c : famolics) {
        cout << "  → " << c->GetName() << " menja!\n";
        c->menjar();
    }
}

void Granja::entretenir() {
    int count = 0;
    for (auto& c : critters) {
        if (c->GetBoredom() > 70) {
            cout << "  → Jugant amb " << c->GetName() << "!\n";
            c->jugar();
            count++;
        }
    }

    if (count == 0) {
        cout << " Cap Critter es troba avorrit!\n";
    }
    else {
        cout << " Has jugat amb " << count << " Critter(s)!\n";
    }
}

int Granja::comptarCritters() const {
    return critters.size();
}

Granja::~Granja() {
    for (auto& c : critters) {
        delete c;
    }
}

