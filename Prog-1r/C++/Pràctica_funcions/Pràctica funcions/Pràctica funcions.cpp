// Pràctica funcions.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//Give me a number

#include <iostream>
#include <string>
using namespace std;
/// <summary> Incuir llibreries i estalviar-nos d'escriure std:: cada vegada que fem un cout o un cin </summary>
/// <returns> No retornem res </returns>
int main()
{
/// <summary> Definir un string per als jugadors, una funció int pels nombres </summary>
/// <returns> No retornem res </returns>
	string Jugadors[3] = {};
	int puntuacio[3] = {};

/// <summary> Fer un bucle for per guardar els noms dels jugadors i les seves puntuacions </summary>
/// <param name="Jugadors"> La variable on guardarem els jugadors </param>
/// <param name="puntuacio"> La variable on guardarem la puntuació dels jugadors </param>
/// <returns> No retornem res </returns>
	for (int i = 0; i < 3; i++) {
		cout << "Escriu el nom del jugador " << (i + 1) << ": ";
		cin >> Jugadors[i];
		cout << "Escriu la puntuacio de " << Jugadors[i] << ": ";
		cin >> puntuacio[i];
		cout << "\n";
	}
	
/// <summary> Fer un bucle for per comunicar les puntuacions dels jugadors i quins l'han aconseguit </summary>
/// <param name="jugadors"> No introduïm nous valors </param>
/// <returns> No retornem res </returns>
	cout << "\nLes puntuacions dels jugadors son: \n";
	for (int i = 0; i < 3; i++) {
		cout << Jugadors[i] << " amb puntuacio: " << puntuacio[i] << ", \n";
	}
	cout << "\n";

/// <summary> Fem una mena de bucle amb funcions while </summary>
/// <returns> Retornem true només per parar el programa </returns>
		if (puntuacio[0] > puntuacio[1] and puntuacio[0] > puntuacio[2]) {
			cout << "\nEl guanyador es : " << Jugadors[0] << " amb " << puntuacio[0] << endl;
			return true;
		}
		else if (puntuacio[0] < puntuacio[1] and puntuacio[2] < puntuacio[1]) {
			cout << "\nEl guanyador es: " << Jugadors[1] << " amb " << puntuacio[1] << endl;
			return true;
		}
		else if (puntuacio[0] < puntuacio[2] and puntuacio[1] < puntuacio[2]) {
			cout << "\nEl guanyador es: " << Jugadors[2] << " amb " << puntuacio[2] << endl;
			return true;
		}
		else {
			cout << "Hi ha varis jugadors que tenen la mateixa puntuacio\n";
		}
	}
