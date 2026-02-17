#include <iostream>
#include "Examen.h"
#include "TriangleEquilater.h"

using namespace std;

int main() {

    cout << "===== EXAMEN DE TRIANGLES =====" << endl;
    cout << "Calcula l'area i el perimetre de cada figura.\n";
    //Menu

    Examen examen;
    //Creem una variable local examen

    Triangle* t1 = new Triangle("Triangle Rectangle", 4, 3, { 3, 4, 5 });
    Triangle* t2 = new TriangleEquilater("Triangle Equilater", 6);
    //Creem dues variables noves pels nostres triangles amb valors default

    examen.afegirTriangle(t1);
    examen.afegirTriangle(t2);
    //Afegirem aquests triangles a examen

    examen.ferExamen();
    examen.corregir();
    //Cridarem les funcions de fer l'exàmen i corregir-lo 

    cout << "\n===== RESULTAT FINAL =====" << endl;
    cout << "Puntuacio obtinguda: "
        << calcularPuntsTotals(examen)
        << " punts." << endl;
    //Acabat l'exàmen calcularem la nota i la direm

    return 0;
}
