// GestordeSuspesos.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <vector>
#include <thread>
#include <chrono>
using namespace std;

void mostrarHistorial(const vector<double>& notes) {
    int suspes = 0, aprovat = 0, notable = 0, excelent = 0;
    for (double nota : notes) {
        if (nota < 5) suspes++;
        else if (nota <= 6.5) aprovat++;
        else if (nota <= 8.5) notable++;
        else excelent++;
    }
            cout << "\nTotal suspesos: " << string(suspes, '*') << endl;
            cout << "Total aprovats: " << string(aprovat, '*') << endl;
            cout << "Total notables: " << string(notable, '*') << endl;
            cout << "Total excelents: " << string(excelent, '*') << endl;
}

void sortintAnimat() {
    const int total = 20;
    for (int i = 0; i <= total; i++) {
        int tantpercent = (i * 100) / total;
        cout << "\nSortint [";
        for (int j = 0; j < total; j++) {
            if (j < i) cout << "=";
            else cout << " ";
        }
        cout << "]" << tantpercent << "%" << flush;
        this_thread::sleep_for(chrono::milliseconds(100));
    }
    cout << "\nPrograma finalitzat!" << endl;
}

int main()
{
    int menu;
    bool obert = true;
    vector<double>notes;
    double nota;
    
    while (obert = true) {
        cout << "\nBenvingut al gestor de notes!\n";
        cout << "##====================##" << endl;
        cout << "Opcions: " << endl;
        cout << "[1 (RT)] Registrar notes" << endl;
        cout << "[2 (MJ)] Consultar la mitjana de classe" << endl;
        cout << "[3 (HT)] Histograma de notes" << endl;
        cout << "[4 (FI)] Sortir" << endl;
        cout << "Tria una opcio: ";
        cin >> menu;
        switch (menu) {
        case 1: //Registrem notes
            cout << "\nEscriu les notes, per acabar introdueix -1 al final de la sequencia" << endl;
            while (true) {
                cin >> nota;
                if (nota == -1) { break; }
                notes.push_back(nota);
            }
            cout << "\nLes notes introduides son les següents: ";
            for (double n : notes) { cout << n << " "; }
            cout << endl;
            break;
        case 2: {//Consultar la mitjana
            if (notes.empty()) { cout << "\nNo hi ha cap nota registrada per calcular la mitjana." << endl; break; }
            double suma = 0.0;
            for (double n : notes) { suma += n; }
            double mitjana = suma / notes.size();
            cout << "\nLa nota mitjana dels alumnes es: " << mitjana << endl << endl;
            break;
        }
        case 3: //Mostrar historial de notes
            if (notes.empty()) {
                cout << "\nEncara no hi ha cap nota a mostrar." << endl;
            }
            else {
                mostrarHistorial(notes);
            }
            break;
        case 4: //Sortir
            cout << "Tancant aplicació..." << endl;
            sortintAnimat();
            return 0;
         default:
            cout << "\nError al introduir input, provi si us plau una altra vegada." << endl;
            break;
        }
    }
}

