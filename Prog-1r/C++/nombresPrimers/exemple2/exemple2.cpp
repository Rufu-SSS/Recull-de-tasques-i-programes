// exemple2.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//

#include <iostream>
#include <cmath>
#include <cstdlib>
#include <chrono>
#include <thread>
/// <summary>
/// La llibreria <iostream> ajudarà a l'usuari a interactuar amb la terminal amb funcions com 'cin o cout'.
/// La llibreria <cmath> ens servirà per fer ús de l'arrel quadrada d'un nombre.
/// La llibreria <cstdlib> ens ajudarà a fer un exit quan ho demanem.
/// Les llibreries <chrono> i <thread> ens ajudaran a implementar pauses durant el procés del programa
/// </summary>
/// -------------------------------------------------------------------------------------------------------
using namespace std;
float number;


unsigned long long factorial(int num) {
	unsigned long long resultat = 1;
	for (int i = 1; i <= num; i++) {
		resultat *= i;
	}
	return resultat;
}
/// <summary>
/// Calcula la factorial d'un nombre enter positiu
/// </summary>
/// <param name="num"> 
/// El nombre enter ha de ser positiu o igual a zero per tal de fer-ne la factorial 
/// </param> 
/// <returns> 
/// El resultat que es retornarà serà un nombre enter sense signe, el 'long long' serveix per representar valors molt grans 
/// </returns>
/// -----------------------------------------------------------------------------------------------------------------------

bool esUnNombrePrimer(int number) {
	if (number == 0 || number == 1 || number == 4) return false; 
		for (int x = 2; x < number / 2; x++) {
			if (number % x == 0) return false;
		}
		return true;
	}
/// <summary>
/// Mira si el nombre enter es primer
/// Per ser enter un nombre ha de ser positiu, major a 1 i divisible per ell mateix i 1
/// </summary>
/// <param name="nombre"> 
/// El nombre a introduïr ha de ser positiu per tal d'iniciar les funcions
/// </param>
/// <returns> 
/// Retorna true quan el nombre sigui primer
/// Retorna false quan el nombre no sigui primer
/// </returns>
/// ------------------------------------------------------------------------------------

void exercici1() {
	cout << "Has triat l'exercici 1! ! !\n";
	cout << "Introdueix un nombre per calcular-ne la factorial: ";
	cin >> number;
	if (number < 0) {
		cout << "La factorial no es pot fer perquè el nombre introduït es negatiu\n";
	}
	else {
		cout << "La factorial de " << number << " es: " << factorial(number) << endl;
	}
}
/// <summary>
/// Dona a l'usuari la opció de calcular la factorial d'un nombre
/// Comprova si es negatiu, en cas afirmatiu avisa a l'usuari perquè el canviï, 
/// en cas negatiu calcula'l i fes-lo sortir per la terminal
/// </summary>
/// ---------------------------------------------------------------------------

void exercici2() {
	cout << "\nHas triat l'exercici 2! ! !\n";
	cout << "Introdueix un nombre per revisar si es un nombre primer: ";
	cin >> number;

	if (esUnNombrePrimer(number)) {
		cout << "\n" << number << " es un nombre primer\n";
	}
	else {
		cout << "\n" << number << " no es un nombre primer\n";
	}
}
/// <summary>
/// Dona a l'usuari la opció de comprovar si un nombre és primer o no
/// Mostra el resultat al terminal
/// </summary>
/// <param name="number">
/// El nombre el qual volem comprovar si és primer
/// </param>
/// <returns>
/// No es retorna cap valor ja que al ser una funció void ho fa per defecte
/// </returns>
/// -----------------------------------------------------------------------

void sortida() {
	cout << "Sortint...\n";
	this_thread::sleep_for(chrono::milliseconds(500));

	cout << "Sortint. . .\n";
	this_thread::sleep_for(chrono::milliseconds(1000));

	cout << "Sortint.  .  .\n";
	this_thread::sleep_for(chrono::milliseconds(1500));

	exit(0);
}
/// <summary>
/// Mostra una petita animació de sortida amb un delay de 2.5 segons (o també 2500 milisegons)
/// Finalitza el programa amb un exit(0);
/// </summary>
/// <param>
/// No es demana cap valor per emmagatzemar dins una variable
/// </param>
/// <returns>
/// No retorna res
/// </returns>
/// ------------------------------------------------------------------------------------------

int main() {
	int exercici;
	bool mostrarExercicis = true;
	while (mostrarExercicis == true) {
		cout << "\n***********************Escull una opcio ***********************\n";
		cout << "*********************__/1. Exercici 1 __/**********************\n";
		cout << "********************__/ 2. Exercici 2 __/ *********************\n";
		cout << "********************__/ 3. Sortir __/ *************************\n";
		cin >> exercici;
		switch (exercici) {
		case 1:
			exercici1();
			break;
		case 2:
			exercici2();
			break;
		case 3:
			sortida();
			mostrarExercicis = false;
		default:
			cout << "Opcio no valida, introdueix els nombres 1 o 2 la proxima vegada. . .\n\n";
			exit(0);
		}
	}
}
/// <summary>
/// Mostra el menú amb les opcions possibles a triar
/// </summary>
/// <returns>
/// Retorna un nombre enter que indica el cas dins el switch que s'iniciarà
/// Si el nombre sobresurt els 3 casos per defecte s'avisarà a l'usuari
/// </returns>
/// ------------------------------------------------------------------------