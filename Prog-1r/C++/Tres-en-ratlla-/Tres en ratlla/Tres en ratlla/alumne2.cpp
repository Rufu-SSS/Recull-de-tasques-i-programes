

//=====================================================================//
// LLIBRERIES I NAMESPACES - continguts
//=====================================================================//
// -> Alumne1.h: el header que defineix les funcions que fa servir dins 
//   el fitxer cpp
// -> Iostream: per fer servir els cout i cin
// -> Using namespace std: per estalviar-nos d'escriure "std::" 
//=====================================================================//
#include "Alumne2.h"
#include <iostream>
#include <cstdlib>
#include <ctime>
using namespace std;

Alumne2::Alumne2(string nom) {
    //=====================================================================//
    // CONSTRUCTOR ALUMNE1 - continguts
    //=====================================================================//
    // Busca i dona valors inicials a les següents variables.
    //=====================================================================//
    this->nom = nom;
    this->simbol = 'X';
    this->victories = 0;
    srand(time(nullptr));
}

//=====================================================================//
// CONJUNT DE FUNCIONS - continguts
//=====================================================================//
// - string Alumne::funcio(): busca i retorna el string nom
// - char Alumne::funcio(): busca i retorna variable char simbol
// - int Alumne::funcio(): busca i retorna la variable victòries
// - void Alumne::funcio(): busca i augmenta victòries en una unitat
// - int Alumne::funcio(): busca i retorna la variable int pos jug1
//=====================================================================//
string Alumne2::getNomJug2() const { return nom; }
char Alumne2::getSimbolJug2() const { return simbol; }
int Alumne2::getVictoriesJug2() const { return victories; }
void Alumne2::incrementarVictoriesJug2() { victories++; }
int Alumne2::triarMovimentJug2() const {
    int pos;
    cout << nom << " (" << simbol << "), tria una posició (0-8): ";
    cin >> pos;
    return pos;
}