
//=====================================================================//
// LLIBRERIES I NAMESPACES - continguts
//=====================================================================//
// -> String: permet treballar amb cadenes de caràcters alfanumèrics
// -> Using namespace std: per estalviar-nos d'escriure "std::" 
//=====================================================================//
#ifndef ALUMNE2_H
#define ALUMNE2_H
#include <string>
using namespace std;


//=====================================================================//
// CLASSE ALUMNE2 - continguts
//=====================================================================//
//  Privat: 
//      - string nom
//      - char simbol
//      - int victories
//  Públic:
//      - Alumne2(...): defineix la funció per a crear-la al cpp
//      - string funcio(): defineix la funció per a crear-la al cpp
//      - char funcio(): defineix la funció per a crear-la al cpp
//      - int funcio(): defineix la funció per a crear-la al cpp
//      - void funcio(): defineix la funció per a crear-la al cpp
//      - int funcio(): defineix la funció per a crear-la al cpp
//=====================================================================//
class Alumne2 {
private:
    string nom;
    char simbol;
    int victories;

public:
    Alumne2(string nom);
    string getNomJug2() const;
    char getSimbolJug2() const;
    int getVictoriesJug2() const;
    void incrementarVictoriesJug2();
    int triarMovimentJug2() const; // demanar posició
};

#endif
