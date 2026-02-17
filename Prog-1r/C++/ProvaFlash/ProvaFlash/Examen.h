#pragma once
#include <iostream>
#include "Triangle.h"
using namespace std;

class Examen {
private:
    vector<Triangle*> triangles;
    vector<float> inputUsuariArea;
    vector<float> inputUsuariPerimetre;
    float punts;
    //Plantilles de variables
public:
    Examen() {
        punts = 0;
    } //Inicialitzarem el constructor examen amb punts=0

    void afegirTriangle(Triangle* t) {
        triangles.push_back(t);
    } //Afegirem els triangles de la classe triangle al vector triangles

    void ferExamen() {
        float areaUsuari, perimetreUsuari;
        for (size_t i = 0; i < triangles.size(); i++) {
            cout << "\n--- Pregunta " << i + 1 << " ---" << endl;
            triangles[i]->mostrarInfo();
            cout << "Introdueix l'area calculada: ";
            cin >> areaUsuari;
            cout << "Introdueix el perimetre calculat: ";
            cin >> perimetreUsuari;
            inputUsuariArea.push_back(areaUsuari);
            inputUsuariPerimetre.push_back(perimetreUsuari);
        }
    } //Preparem un mini-test de dues preguntes perquè l'usuari el fagi

    void corregir() {
        punts = 0;
        for (size_t i = 0; i < triangles.size(); i++) {
            float areaCorrecta = triangles[i]->calcularArea();
            float perimetreCorrecte = triangles[i]->calcularPerimetre();
            if (abs(inputUsuariArea[i] - areaCorrecta) < 0.1) punts += 1;
            if (abs(inputUsuariPerimetre[i] - perimetreCorrecte) < 0.1) punts += 1;
        }
    } //Funció que corregirà les preguntes segons siguin correctes o no

    float getPunts() const {
        return punts;
    } //Getter que capta el valor dinal de la variable punts després de corregir l'examen

    friend float calcularPuntsTotals(const Examen& e);
    //Plantilla de funció amiga
};
