
//=====================================================================//
// LLIBRERIES I NAMESPACES - continguts
//=====================================================================//
// -> String: permet treballar amb cadenes de caràcters alfanumèrics
// -> Using namespace std: per estalviar-nos d'escriure "std::" 
//=====================================================================//
#ifndef ALUMNE1_H
#define ALUMNE1_H
#include <string>
using namespace std;


//=====================================================================//
// CLASSE ALUMNE1 - continguts
//=====================================================================//
//  Privat: 
//      - string nom
//      - char simbol
//      - int victories
//  Públic:
//      - Alumne1(...): defineix la funció per a crear-la al cpp
//      - string funcio(): defineix la funció per a crear-la al cpp
//      - char funcio(): defineix la funció per a crear-la al cpp
//      - int funcio(): defineix la funció per a crear-la al cpp
//      - void funcio(): defineix la funció per a crear-la al cpp
//      - int funcio(): defineix la funció per a crear-la al cpp
//=====================================================================//
class Alumne1 {
private:
    string nom;
    char simbol;
    int victories;

public:
    Alumne1(string nom);
    string getNomJug1() const;
    char getSimbolJug1() const;
    int getVictoriesJug1() const;
    void incrementarVictoriesJug1();
    int triarMovimentJug1() const; // demanar posició
};

#endif
