#include "Critter(classe).h"
#include "Granja.h"

void clearScreen() {
#ifdef _WIN32
	system("cls");
#else 
	system("clear");
#endif
}

void pausar() {
	cout << "\n Pressiona Enter per continuar el joc...";
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
	cin.get();
}

void mostrarTitol() {
	cout << R"(
    |=======================================|
    |           GRANJA DE CRITTERS          |
    |        Cuida les teves mascotes!      |
    |=======================================|
)" << endl;
}

void mostrarMenu() {
	cout << "\n|================ MENU PRINCIPAL ================|\n";
	cout << "|  1️- Crear Critter Normal                      |\n";
	cout << "|  2️- Crear Critter Especial                    |\n";
	cout << "|  3️- Llistar tots els Critters                 |\n";
	cout << "|  4️- Interactuar amb un Critter                |\n";
	cout << "|  5️- Combinar dos Critters (reproduir)         |\n";
	cout << "|  6️- Alimentar tots els famolics               |\n";
	cout << "|  7️- Entretenir tots els avorrits              |\n";
	cout << "|  8️- Passar el temps (afegir 10 min al temps)  |\n";
	cout << "|  9- Buscar Critter per nom                     |\n";
	cout << "|  10- Estadistiques de la granja                |\n";
	cout << "|  0️- Sortir del joc                            |\n";
	cout << "|================================================|\n";
	cout << "Tria una opcio: ";
}
void crearCritterNormal(Granja& granja) {
    clearScreen();
    cout << "\n CREAR CRITTER NORMAL\n";
    cout << "Nom del Critter: ";
    string nom;
    cin >> nom;

    int gana = rand() % 50;
    int avorriment = rand() % 50;

    Critter* nouCritter = new Critter(nom, gana, avorriment);
    granja.afegirCritter(nouCritter);

    cout << "\nv " << nom << " ha nascut a la granja!\n";
    nouCritter->Greet();
    pausar();
}

void crearCritterEspecial(Granja& granja) {
    clearScreen();
    cout << "\n CREAR CRITTER ESPECIAL\n";
    cout << "Nom del Critter Especial: ";
    string nom;
    cin >> nom;

    CritterEspecial* nouEspecial = new CritterEspecial(nom);
    granja.afegirCritter(nouEspecial);

    cout << "\nv " << nom << " (Especial) ha nascut a la granja!\n";
    nouEspecial->Greet();
    pausar();
}

void llistarCritters(Granja& granja) {
    clearScreen();
    cout << "\n LLISTA DE CRITTERS\n";
    granja.llistarCritters();
    pausar();
}

void interactuarAmbCritter(Granja& granja) {
    clearScreen();
    cout << "\n INTERACTUAR AMB CRITTER\n";
    cout << "Nom del Critter: ";
    string nom;
    cin >> nom;

    Critter* critter = granja.buscarCritterPerNom(nom);

    if (critter == nullptr) {
        cout << "\n No hem trobat cap Critter amb aquest nom!\n";
        pausar();
        return;
    }

    clearScreen();
    critter->showStatus();

    cout << "\nQue vols fer?\n";
    cout << "1 - Donar menjar \n";
    cout << "2 - Jugar \n";
    cout << "3 - Escoltar \n";

    // Comprovar si és especial
    CritterEspecial* especial = dynamic_cast<CritterEspecial*>(critter);
    if (especial != nullptr) {
        cout << "4 - Usar habilitat especial \n";
    }

    cout << "0 - Tornar\n";
    cout << "Opcio: ";

    int accio;
    cin >> accio;

    switch (accio) {
    case 1:
        critter->menjar();
        break;
    case 2:
        critter->jugar();
        break;
    case 3:
        critter->escoltar();
        break;
    case 4:
        if (especial != nullptr) {
            especial->usarHabilitat();
        }
        else {
            cout << "Aquest Critter no es especial!\n";
        }
        break;
    case 0:
        return;
    default:
        cout << "Opcio invàlida!\n";
    }

    cout << "\n--- ESTAT ACTUALITZAT ---\n";
    critter->showStatus();
    pausar();
}

void combinarCritters(Granja& granja) {
    clearScreen();
    cout << "\n COMBINAR CRITTERS (REPRODUCCIO)\n";

    cout << "Nom del primer Critter: ";
    string nom1;
    cin >> nom1;

    cout << "Nom del segon Critter: ";
    string nom2;
    cin >> nom2;

    Critter* c1 = granja.buscarCritterPerNom(nom1);
    Critter* c2 = granja.buscarCritterPerNom(nom2);

    if (c1 == nullptr || c2 == nullptr) {
        cout << "\n Un o tots dos Critters no existeixen!\n";
        pausar();
        return;
    }

    Critter* fill = new Critter((*c1) + (*c2));
    granja.afegirCritter(fill);

    cout << "\n Els critters " << nom1 << " i " << nom2 << " han tingut un fill!\n";
    fill->Greet();
    fill->showStatus();
    pausar();
}

void alimentarFamolics(Granja& granja) {
    clearScreen();
    cout << "\n ALIMENTAR ELS CRITTERS AMB GANA\n";
    granja.alimentarFamolics();
    pausar();
}

void entretenir(Granja& granja) {
    clearScreen();
    cout << "\n ENTRETENIR ELS CRITTERS AVORRITS\n";
    granja.entretenir();
    pausar();
}

void passarTemps(Granja& granja, int& tempsTotal) {
    clearScreen();
    cout << "\n PASSAR EL TEMPS...\n";
    granja.tempsPassaPerTots(10);
    tempsTotal += 10;
    cout << "Han passat 10 minuts. Total: " << tempsTotal << " minuts.\n";

    // Avisos
    auto famolics = granja.buscarCrittersGana();
    if (!famolics.empty()) {
        cout << "\n ATENCIO: " << famolics.size() << " Critter(s) tenen gana!\n";
    }

    pausar();
}

void buscarCritter(Granja& granja) {
    clearScreen();
    cout << "\n BUSCAR CRITTER\n";
    cout << "Nom del Critter: ";
    string nom;
    cin >> nom;

    Critter* trobat = granja.buscarCritterPerNom(nom);

    if (trobat != nullptr) {
        cout << "\n Critter trobat!\n";
        trobat->showStatus();
    }
    else {
        cout << "\n No hem trobat cap Critter amb aquest nom.\n";
    }

    pausar();
}

void mostrarEstadistiques(Granja& granja) {
    clearScreen();
    cout << "\n ESTADISTIQUES DE LA GRANJA\n";
    cout << "================================\n";

    auto especials = granja.buscarSpecialCritters();
    auto famolics = granja.buscarCrittersGana();

    cout << " Total Critters: " << granja.comptarCritters() << endl;
    cout << " Critters Especials: " << especials.size() << endl;
    cout << " Critters amb Gana (>70): " << famolics.size() << endl;

    pausar();
}

int main() {
    srand(time(0));  // Seed per random

    Granja laGranja;
    int tempsTotal = 0;
    int opcio;

    clearScreen();
    mostrarTitol();

    cout << "\n Benvingut al simulador de granja de Critters!\n";
    cout << "Crea critters, cuidals tots i fes que creixin sans.\n";
    pausar();

    do {
        clearScreen();
        mostrarTitol();
        cout << " Temps transcorregut: " << tempsTotal << " minuts\n";
        mostrarMenu();

        cin >> opcio;

        switch (opcio) {
        case 1:
            crearCritterNormal(laGranja);
            break;
        case 2:
            crearCritterEspecial(laGranja);
            break;
        case 3:
            llistarCritters(laGranja);
            break;
        case 4:
            interactuarAmbCritter(laGranja);
            break;
        case 5:
            combinarCritters(laGranja);
            break;
        case 6:
            alimentarFamolics(laGranja);
            break;
        case 7:
            entretenir(laGranja);
            break;
        case 8:
            passarTemps(laGranja, tempsTotal);
            break;
        case 9:
            buscarCritter(laGranja);
            break;
        case 10:
            mostrarEstadistiques(laGranja);
            break;
        case 0:
            clearScreen();
            cout << "\nGracies per jugar a la Granja de Critters!\n";
            cout << "Temps total de joc: " << tempsTotal << " minuts\n";
            break;
        default:
            cout << "\n Opcio invalida!\n";
            pausar();
        }

    } while (opcio != 0);

    return 0;
}