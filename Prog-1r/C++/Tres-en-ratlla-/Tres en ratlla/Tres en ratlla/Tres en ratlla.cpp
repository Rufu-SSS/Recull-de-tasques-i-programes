


//=====================================================================//
// LLIBRERIES I NAMESPACES - continguts
//=====================================================================//
// -> Iostream: per fer servir els cout i cin
// -> Vector: per emmagatzemar info dins "caixes"
// -> Stdexcept: per estalviar-nos els errors
// -> Header Alumne1.h: funcions bàsiques pel funcionament del programa
// -> Header Alumne2.h: funcions bàsiques pel funcionament del programa
// -> Algorithm: per a fer servir funcions matemàtiques
// -> Using namespace std: per estalviar-nos d'escriure "std::" 
//=====================================================================//
#include <iostream>
#include <vector>
#include <stdexcept>
#include "Alumne1.h"
#include "Alumne2.h"
#include <algorithm>
using namespace std;


class Joc {
//=====================================================================//
// CLASSE JOC - continguts
//=====================================================================//
// Privat:
//     - Vector tauler: emmagatzema la info que posem al tauler
//     - Alumne1*: creem un punter fent referència a alumne1
//     - Alumne2*: creem un altre punter fent referència a alumne2
//     - Char tornActual: creem una variable que digui de qui es el torn
// Públic:
//     - Funció Joc(...): constructor que lliga els punters amb variables,
//     inicia el tauler i assigna el torn inicial/actual al jugador 1 amb 
//     una altra funció.
//     - Funció inicialitzarTauler(): al principi de cada partida, tant 
//     sigui la primera com la pròxima, inicialitzarem el tauler a les 
//     mides desitjades i afegirem espais en blanc per a posicions en 
//     concret.
//     - Funció imprimirTauler(): tal com diu la funció, imprimeix el 
//       tauler del joc amb els espais en blanc creats a partir de la
//     funció anterior (tauler=vector<...>(9, ' ');).
//     - Funció esMovimentVàlid(...): comprova si es pot jugar una fitxa
//     en el teu torn en una posició en concret, en cas que no es pugui 
//     que avisi.
//     - Funció ferMoviment(...): juga el teu torn la posició (si no està
//     ja ocupada) i crida la funció anterior per revisar si s'hi pot 
//     jugar o no.
//     - Funció comprovarGuanyador(): Segons el torn que sigui i 
//     pràcticament totes les combinacions de victòria, enviem el missatge
//     de victòria pel jugador que ha guanyat.
//     - Funció taulaPlena(): quan no es puguin jugar més 'fitxes' a les 
//     posicions dels taulers, avisa i canvia l'estat del booleà a true.
//     - Funció canviarTorn(): tal com diu, canvia el torn del jugador 1
//     al jugador 2, res més que això.
//     - Funció reiniciarPartida(): quan els jugadors vulguin fer una 
//     altra partida després de l'anterior, re-inicialitzarem el tauler 
//     com estava al principi (buit) i assignarem el torn actual al jug1.
//     - Funció jugar(): fes tot el bucle del joc, imprimeix el tauler i 
//     crea un bucle on mentre la condició sigui certa/true funcioni tot:
//     assigna el torn actual als jugadors amb les funcions de les classes.
//     També mira qui guanya o empata i ho diu per consola, al final 
//     pregunta si es vol reiniciar la partida i fer-ne una altra.
//=====================================================================//
private:
    vector<char> tauler;
    Alumne1* alumne1;
    Alumne2* alumne2;
    char tornActual;

public:
    Joc(Alumne1* a1, Alumne2* a2) {
        alumne1 = a1;
        alumne2 = a2;
        inicialitzarTauler();
        tornActual = alumne1->getSimbolJug1();
    }

    void inicialitzarTauler() { tauler = vector<char>(9, ' '); }
    void imprimirTauler() const {
        cout << "\n";
        cout << " " << tauler[0] << " | " << tauler[1] << " | " << tauler[2] << endl;
        cout << "---|---|---" << endl;
        cout << " " << tauler[3] << " | " << tauler[4] << " | " << tauler[5] << endl;
        cout << "---|---|---" << endl;
        cout << " " << tauler[6] << " | " << tauler[7] << " | " << tauler[8] << endl;
        cout << "\n";
    }
    bool esMovimentValid(int pos) const { return (pos >= 0 && pos < 9 && tauler[pos] == ' '); }
    void ferMoviment(int pos) {
        if (!esMovimentValid(pos))
            throw invalid_argument(" Posició no vàlida!");
        tauler[pos] = tornActual;
    }
    bool comprovarGuanyador() const {
        char s = tornActual;
        for (int i = 0; i < 3; i++) {
            if (tauler[i * 3] == s && tauler[i * 3 + 1] == s && tauler[i * 3 + 2] == s)
                return true;
        }

        for (int i = 0; i < 3; i++) {
            if (tauler[i] == s && tauler[i + 3] == s && tauler[i + 6] == s)
                return true;}

        if ((tauler[0] == s && tauler[4] == s && tauler[8] == s) ||
            (tauler[2] == s && tauler[4] == s && tauler[6] == s))
            return true;

        return false;
    }
    bool taulaPlena() const {
        for (char c : tauler)
            if (c == ' ') return false;
        return true;
    }
    void canviarTorn() {
        tornActual = (tornActual == alumne1->getSimbolJug1()) ? alumne2->getSimbolJug2() : alumne1->getSimbolJug1();
    }
    void reiniciarPartida() {
        inicialitzarTauler();
        tornActual = alumne1->getSimbolJug1();
    }
    void jugar() {
        int pos;
        while (true) {
            imprimirTauler();

            if (tornActual == alumne1->getSimbolJug1()) { pos = alumne1->triarMovimentJug1(); }
            else { pos = alumne2->triarMovimentJug2(); }
            try { ferMoviment(pos);
                if (comprovarGuanyador()) { imprimirTauler();
                    if (tornActual == alumne1->getSimbolJug1()) {
                        cout << " " << alumne1->getNomJug1() << " ha guanyat!\n";
                        alumne1->incrementarVictoriesJug1(); }
                    else { cout << " " << alumne2->getNomJug2() << " ha guanyat!\n";
                        alumne2->incrementarVictoriesJug2(); }
                    break; }
                if (taulaPlena()) { imprimirTauler(); cout << " Empat!\n"; break; }
                canviarTorn(); }
            catch (const invalid_argument& e) { cout << e.what() << endl; } } 
        reiniciarPartida();
    } 
};


int main() {
//=====================================================================//
// FUNCIÓ PRINCIPAL - continguts
//=====================================================================//
// Variables inicials: 
//     - Bool jugant
//     - String resposta, nom1, nom2
//     - Alumne1 a1(nom1); Alumne2 a2(nom2);
//     - Joc joc(&a1, &a2);
// Bucle de joc:
//     - While(jugant==true): mentre es compleixi la condició que fa true
//     el bucle, repetirà infinites vegades el contingut dins seu. Crida 
//     la funció jugar() de la classe joc que permet a l'usuari 
//     interactuar amb el terminal i en finalitzar pregunta si vol 
//     continuar jugant o no, en cas de posar malament l'input avisarà a 
//     l'usuari, tot seguit donarà els resultats finals de victòries i 
//     compararà els stats dels dos jugadors per veure qui ha guanyat més 
//     vegades.
//=====================================================================//
    bool jugant=true;
    string resposta;
    string nom1, nom2; cout << "Nom Alumne1 (X): "; cin >> nom1;
    cout << "Nom Alumne2 (O): "; cin >> nom2; 
    Alumne1 a1(nom1); Alumne2 a2(nom2);
    Joc joc(&a1, &a2); 
    
    while (jugant == true) {
        joc.jugar();
        cout << "\n Vols continuar jugant? (S/Si/SI o N/No/NO): ";
        cin >> resposta;
        transform(resposta.begin(), resposta.end(), resposta.begin(), ::toupper);
        if (resposta == "S"  || resposta == "SI") {
            cout << "El joc continua!" << endl;

        }
        else if (resposta == "N" || resposta == "NO") {
            cout << "Has triat plegar!" << endl;
            jugant = false;
            break;
        }
        else {
            cout << "Resposta no vàlida!" << endl;
        }
    }
    cout << "\n--- RESULTATS FINALS ---\n";
    cout << a1.getNomJug1() << " victòries: " << a1.getVictoriesJug1() << endl;
    cout << a2.getNomJug2() << " victòries: " << a2.getVictoriesJug2() << endl;

    if (a1.getVictoriesJug1() > a2.getVictoriesJug2()) {
        cout << "El jugador " << a1.getNomJug1() << " ha guanyat més partides que el jugador " << a2.getNomJug2(); }
    else {
        cout << "El jugador " << a2.getNomJug2() << " ha guanyat més partides que el jugador " << a1.getNomJug1();
    }
    return 0;
}
