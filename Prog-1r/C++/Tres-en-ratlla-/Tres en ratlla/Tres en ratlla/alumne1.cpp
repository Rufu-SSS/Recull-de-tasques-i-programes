

//=====================================================================//
// LLIBRERIES I NAMESPACES - continguts
//=====================================================================//
// -> Alumne1.h: el header que defineix les funcions que fa servir dins 
//   el fitxer cpp
// -> Iostream: per fer servir els cout i cin
// -> Using namespace std: per estalviar-nos d'escriure "std::" 
//=====================================================================//
#include "Alumne1.h"
#include <iostream>
using namespace std;

Alumne1::Alumne1(string nom) {
//=====================================================================//
// CONSTRUCTOR ALUMNE1 - continguts
//=====================================================================//
// Busca i dona valors inicials a les següents variables.
//=====================================================================//
    this->nom = nom;
    this->simbol = 'X';
    this->victories = 0;
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
string Alumne1::getNomJug1() const { return nom; }
char Alumne1::getSimbolJug1() const { return simbol; }
int Alumne1::getVictoriesJug1() const { return victories; }
void Alumne1::incrementarVictoriesJug1() { victories++; }
int Alumne1::triarMovimentJug1() const {
    int pos;
    cout << nom << " (" << simbol << "), tria una posició (0-8): ";
    cin >> pos;
    return pos;
}
