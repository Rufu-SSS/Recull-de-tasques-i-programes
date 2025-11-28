#include <iostream>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <Windows.h>
using namespace std;
// =====================================================================================//
// Llibreries que fem servir:
// Iostream: escriure per la consola
// Cmath: fer operacions com per exemple arrels
// Cstdlib: per crear els mètodes que generen nombres aleatoris (el seed creator 
// i el generador)
// Ctime: manipular dades com el temps o dates
// Window.h: només per obligar els bucles a parar un temps curt
// =====================================================================================//


// =====================================================================================//
// Estructura Enemy
// Que fa:
//  Definim l'estructura Enemy i hi afegeix vàries variables: variables d'ID per a 
//  diferenciar-los, floats x/y per a les posicions on es generen i una vida que 
//  aleatoritarem més endavant.
// =====================================================================================//
struct Enemy {
    int id;
    float x;
    float y;
    int hp;
};

// =====================================================================================//
// Funció trobarEnemic
// Que fa:
//  Començant pel nombre 0 i aleatoritzant una variable dins 'millorDist', entrarem en un
//  bucle que aleatoritza també una variable però dins 'dist'. Dins el mateix bucle es 
//  farà una petita comparació entre les dues variables que aleatoritzen les posicions en
//  eixos X/Y i s'escollirà 'millorDist' sempre que sigui major als valors de 'dist'.
//  Ja que hi som simulem al terminal uns textos que representarien el funcionament d'un
//  sistema inventat que detectaria ID, adreça i distància entre els enemics i un jugador
//  imaginari.
// =====================================================================================//
Enemy* trobarEnemic(Enemy* arr, int n) {
    int indexProper = 0;
    float millorDist = sqrt(arr[0].x * arr[0].x + arr[0].y * arr[0].y);

    for (int i = 1; i < n; i++) {
        float dist = sqrt(arr[i].x * arr[i].x + arr[i].y * arr[i].y);
        if (dist < millorDist) {
            millorDist = dist;
            indexProper = i;
        }
    }

    cout << "\n[Targeting System Activated]\n";
    cout << "Enemy mes proper → ID " << arr[indexProper].id
        << " (addr: " << &arr[indexProper] << ")\n";
    cout << "Distancia: " << millorDist << endl;

    return &arr[indexProper];
}

// =====================================================================================//
// Funció dany
// Que fa:
//  Restar vida als enemics en una quantitat i tot seguit comunicar que l'han mort si la 
//  seva vida arriba a ser menor o igual a 0.
// =====================================================================================//
void dany(Enemy* e, int amount) {
    int old = e->hp;
    e->hp -= amount;

    cout << "Enemy " << e->id << " HP: "
        << old << " → " << e->hp << endl;
    if (e->hp <= 0) { cout << "Enemy destroyed!" << endl; }
}

// =====================================================================================//
// MAIN
// Que fa:
//  Doncs al ser la funció principal on tot el codi es af servir, ara es farà ús de les 
//  dues funcions anteriors: trobarEnemic i dany. Amb un generador de nombres aleatoris,
//  prepararem una 'seed' amb la qual generar nombres aleatoris, tot seguit preguntarem a
//  l'usuari quants enemics vol que li generem. (Consta que per molts enemics que l'usuari
//  demani, el resultat sempre serà el mateix perquè el programa només en mira un d'enemic).
//  Es busca que en generi més de tres per funcionar, aquests els guarea en un punter de nom
//  'enemies' i en una posició (així és més fàcil trobar-los dins el punter). 
//  Omplim el punter de dades inventades que es generen aleatòriament menys l'ID perquè
//  sempre comença a partir de 0. Es comuniquen el total d'enemics generats i després els 
//  apliquem el dany que l'usuari vulgui, al rebre aquest dany la funció dany(...) imprimirà
//  el seu text i després obligarem el bucle a descansar / fer sleep 100 ms.
//  Després d'acabar amb tots els enemics netejarem la memòria del punter i tot seguit
//  retornarem 0.
// =====================================================================================//
int main() {
    srand(time(NULL));
    int n;
    int danyRebut;

    cout << "Quants enemics vols generar (minim 3)? ";
    cin >> n;
    if (n < 3) {
        cout << "ERROR: minim 3 enemics." << endl;
        return 0;
    }
    cout << "\nQuant dany vols que rebin els enemics?";
    cin >> danyRebut;


    Enemy* enemies = new Enemy[n];
    for (int i = 0; i < n; i++) {
        enemies[i].id = i + 1;
        enemies[i].x = (rand() % 200 - 100) / 10.0f; // Rang random (-10 a 10)
        enemies[i].y = (rand() % 200 - 100) / 10.0f; // Rang random (-10 a 10)
        enemies[i].hp = 20 + rand() % 31; // Rang random (20 a 50)
    }

    cout << "\nEnemics generats: " << n << endl;
    Enemy* target = trobarEnemic(enemies, n);

    for (int i = 0; i < n; i++) {
        cout << "\n--- Atacant enemic nº" << enemies[i].id << " ---\n";

        while (enemies[i].hp > 0) {
            dany(&enemies[i], danyRebut);
            Sleep(100);
        }
    }
    delete[] enemies;
    return 0;
}