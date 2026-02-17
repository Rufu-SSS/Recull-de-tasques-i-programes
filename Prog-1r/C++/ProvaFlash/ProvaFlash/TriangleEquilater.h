#pragma once
#include "Triangle.h"

class TriangleEquilater : public Triangle {
private:
    float costat;
    //Variable privada
public:
    TriangleEquilater(string d, float c) {
        descripcio = d;
        costat = c;
        base = c;
        altura = (1.732 * c) / 2;
        costats = { c, c, c };
    } //Constructor plantilla

    float calcularArea() override {
        return (1.732 / 4) * costat * costat; // <- area, no perimetre
    } //Funció que calcula l'àrea del triangle equilater

    float calcularPerimetre() override {
        return 3 * costat;
    } //Funció que calcula el perímetre del triangle equilàter
};