#pragma once
#include <iostream>
#include <vector>
using namespace std;

class Triangle {
public:
    Triangle() {
        descripcio = "Triangle no equilater";
        base = 0;
        altura = 0;
        costats = { 0, 0, 0 };
    } //Constructor amb valors inicialtizats a 0

    Triangle(string d, int b, int a, vector<float> c) {
        descripcio = d;
        base = b;
        altura = a;
        costats = c;
    } //Constructor plantilla

    virtual float calcularArea();
    virtual float calcularPerimetre();
    virtual void mostrarInfo();
    //Plantilles de funcions

protected:
    string descripcio;
    int base;
    int altura;
    vector<float> costats;
    //Plantilles de variables
};