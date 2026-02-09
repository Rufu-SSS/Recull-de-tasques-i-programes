#include <iostream>
#include <cstdlib>
#include <ctime>
#include <vector>
using namespace std;

//====================================================================================================================================//
// INCLOURE LLIBRERIES I NAMESPACES
//====================================================================================================================================//
// Incloem iostream per captar inputs i treure outputs a la consola en format de text, cstdlib per poder fer servir els generadors
// de nombres aleatoris (rand() i srand()), després ctime el farem servir per a fer rellotges interns (però el fem servir juntament amb
// els generadors de nombres aleatoris rand() i srand()) i vector per poder emmagatzemar dades dins variables que actuen com "caixes" 
// on hi bolquem informació i funcionalitats.
//====================================================================================================================================//

class BlackjackGame {
public:
    BlackjackGame() : saldo(1000.0) {}

    void jugar() {
        cout << "Benvingut al Blackjack!\n\n";
        while (true) {
            cout << "Saldo actual: $" << saldo << "\n\n";
            if (saldo <= 0) {
                cout << "Ja no tens diners, el joc acaba.\n";
                break;
            }

            double aposta;
            cout << "Introdueix la quantitat de diners a apostar: $";
            cin >> aposta;

            if (aposta > saldo) {
                cout << "No tens prou diners per aquesta aposta.\n\n";
                continue;
            }

            jugarMa(aposta);
            cout << "\nVols jugar de nou? (s/n): ";
            char resposta;
            cin >> resposta;
            if (resposta == 'n' || resposta == 'N') {
                break;
            }
            cout << "\n";
        }
    }
    //====================================================================================================================================//
    // FUNCIONS I VARIABLES PÚBLIQUES
    //====================================================================================================================================//
    // Definim les variables i les funcions, com que només treballem amb un fitxer .cpp no les cridariem des d'altres llocs però igualment
    // ja estàn bé com a públiques.
    // Definim el constructor del joc sencer (BlackjackGame() i li afegim la quantitat de diners inicials que tindrà el jugador a cada inici
    // de partida, 1000$) i tot seguit la funció de poder jugar (on li ficarem el bucle del joc).
    //====================================================================================================================================//
    // · Funcions en total que hi ha dins: BlackJackGame(), void jugar(), jugarMa(...)
    //====================================================================================================================================//

private:
    double saldo;

    int obtenirCarta() {
        return rand() % 10 + 1;
    }

    int calcularPunts(const vector<int>& cartes) {
        int total = 0;
        for (int carta : cartes) {
            total += carta;
        }
        return total;
    }

    void jugarMa(double aposta) {
        vector<int> cartesJugador;
        vector<int> cartesDealer;

        cartesJugador.push_back(obtenirCarta());
        cartesJugador.push_back(obtenirCarta());
        cartesDealer.push_back(obtenirCarta());
        cartesDealer.push_back(obtenirCarta());

        cout << "\nCartes del dealer: " << cartesDealer[0] << " ?\n";
        cout << "La teva suma de cartes: " << calcularPunts(cartesJugador) << "\n\n";

        bool jugadorEsPlantat = false;
        while (!jugadorEsPlantat && calcularPunts(cartesJugador) < 21) {
            cout << "Vols una altra carta? (s/n): ";
            char resposta;
            cin >> resposta;
            if (resposta == 's' || resposta == 'S') {
                cartesJugador.push_back(obtenirCarta());
                cout << "La teva nova suma de cartes: " << calcularPunts(cartesJugador) << "\n\n";
            }
            else {
                jugadorEsPlantat = true;
            }
        }

        int puntsJugador = calcularPunts(cartesJugador);
        if (puntsJugador > 21) {
            cout << "Has sobrepassat 21! Has perdut $" << aposta << ".\n";
            saldo -= aposta;
            return;
        }

        cout << "Cartes del dealer: ";
        for (int carta : cartesDealer) cout << carta << " ";
        cout << "(Total: " << calcularPunts(cartesDealer) << ")\n";

        while (calcularPunts(cartesDealer) < 17) {
            cartesDealer.push_back(obtenirCarta());
            cout << "El dealer agafa una altra carta. Total: " << calcularPunts(cartesDealer) << "\n";
        }

        int puntsDealer = calcularPunts(cartesDealer);
        if (puntsDealer > 21 || puntsJugador > puntsDealer) {
            cout << "Has guanyat $" << aposta << "!\n";
            saldo += aposta;
        }
        else if (puntsJugador == puntsDealer) {
            cout << "Empat! No es perden els diners apostats.\n";
        }
        else {
            cout << "Has perdut $" << aposta << ".\n";
            saldo -= aposta;
        }
    }
    //====================================================================================================================================//
    // FUNCIONS I VARIABLES PRIVADES
    //====================================================================================================================================//
    // Definim aquí també variables i funcions, en aquest altre cas les fem privades perquè podem, no ens fa falta que siguin totes 
    // públiques. Crearem i emplenarem funcions amb el contingut que volem que facin quan les cridem, principalment les que ajuden a la 
    // jugabilitat del mini-joc, siguent aquestes l'obtenció de cartes (obtenirCarta()), el càlcul de punts (calcularPunts(...)) i si
    // el jugador vol jugar la seva mà (jugarMa(...)), tant si vol més cartes com si no es té en compte com si estigués jugant.
    //====================================================================================================================================//
    // · Funcions en total que hi ha dins: obtenirCarta(), calcularPunts(...) i jugarMa(...), la resta són càlculs i condicionals.
    //====================================================================================================================================//

};

int main() {
    srand(static_cast<unsigned int>(time(0)));

    BlackjackGame joc;
    joc.jugar();

    return 0;
}
//====================================================================================================================================//
// FUNCIÓ PRINCIPAL
//====================================================================================================================================//
// Obrirem un generador de nombres aleatoris, cridarem la classe amb nom BlackjackGame i en crearem una instància (joc), tot seguit, 
// a sobre d'aquesta mateixa instància cridarem la funció jugar(), la qual cridarà el bucle de joc que permet interactuar amb el dealer
// fins que l'usuari se'n cansi i plegui.
// · És un mode de joc infinit, fins que l'usuari continui jugant se seguirà repartint cartes i guanyant/perdent diners, és decisió 
//  de l'usuari plegar de jugar (també depèn de la seva addicció a jugar al blackjack i les apostes).
//====================================================================================================================================//
