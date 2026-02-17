#include "Triangle.h"

float Triangle::calcularArea() {
    return (altura * base) / 2.0; 
} //Funció que calcula l'àrea del triangle normal

float Triangle::calcularPerimetre() {
    return costats[0] + costats[1] + costats[2];
} //Funció que calcula el perímetre del triangle normal

void Triangle::mostrarInfo() {
    cout << "\nDescripcio: " << descripcio << "\n";
    cout << "Base: " << base << "\n";
    cout << "Altura: " << altura << "\n";
    cout << "Costats: ";
    for (float c : costats) {
        cout << c << " ";
    }
    cout << "\n";
} //Mostrem informació bàsica però no les respostes