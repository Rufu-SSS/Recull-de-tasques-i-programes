#include "daus.h"
//Includes --------------------------------------------------------------------------------------------//
// QUE BUSQUEM?
//  Importar les llibreries necessàries per tot el projecte i també la definició de la funció tiraDaus()
//* * *

int tiraDaus() {
	int menu = rand() % 12 + 1;
	//Includes ----------------------------------------------------------------------------------------//
	// QUE BUSQUEM?
	//  Busquem lligar la variable menu amb un rand() amb rang per tal de quan es generin nombres 
	//  aleatoris, aquests mateixos es lliguin amb el bucle switch i segons els resultats apareixin els
	//  daus que simulem que tirem
	//* * *

	switch (menu) {

	case 1:
		cout << "   +-----------+\n";
		cout << "  /             \\\n";
		cout << " |       *       |\n";
		cout << "  \\             /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 1\n";
		break;
	case 2:
		cout << "   +-----------+\n";
		cout << "  /             \\\n";
		cout << " |     *   *     |\n";
		cout << "  \\             /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 2\n";
		break;
	case 3:
		cout << "   +-----------+\n";
		cout << "  /             \\\n";
		cout << " |   *   *   *   |\n";
		cout << "  \\             /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 3\n";
		break;
	case 4:
		cout << "   +-----------+\n";
		cout << "  / *       *   \\\n";
		cout << " |               |\n";
		cout << "  \\ *       *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 4\n";
		break;
	case 5:
		cout << "   +-----------+\n";
		cout << "  / *       *   \\\n";
		cout << " |      *        |\n";
		cout << "  \\ *       *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 5\n";
		break;
	case 6:
		cout << "   +-----------+\n";
		cout << "  / *   *   *   \\\n";
		cout << " |               |\n";
		cout << "  \\ *   *   *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 6\n";
		break;
	case 7:
		cout << "   +-----------+\n";
		cout << "  / *   *   *   \\\n";
		cout << " |       *       |\n";
		cout << "  \\ *   *   *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 7\n";
		break;
	case 8:
		cout << "   +-----------+\n";
		cout << "  / *   *   *   \\\n";
		cout << " |   *       *   |\n";
		cout << "  \\ *   *   *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 8\n";
		break;
	case 9:
		cout << "   +----------+\n";
		cout << "  / *   *   *   \\\n";
		cout << " |   *   *   *   |\n";
		cout << "  \\ *   *   *   /\n";
		cout << "   +----------+\n";
		cout << "\nDau 9\n";
		break;
	case 10:
		cout << "   +-----------+\n";
		cout << "  / *   *   *   \\\n";
		cout << " |    *   * * *  |\n";
		cout << "  \\ *   *   *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 10\n";
		break;
	case 11:
		cout << "   +-----------+\n";
		cout << "  / * * *   *   \\\n";
		cout << " |   *   * * *   |\n";
		cout << "  \\ *   *   *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 11\n";
		break;
	case 12:
		cout << "   +-----------+\n";
		cout << "  / * * *   *   \\\n";
		cout << " |   *   * * *   |\n";
		cout << "  \\ * * *   *   /\n";
		cout << "   +-----------+\n";
		cout << "\nDau 12\n";
		break;
	default:
		cout << "Error...\n";
		break;
	}
	//Includes --------------------------------------------------------------------------------------------//
    // QUE BUSQUEM?
	//  Volem que es mostri per pantalla els resultats de tirar els daus D12. Abans que el programa funcionés,
	//  hi havia el default per tal de saber o estar més aprop de saber on eren els errors.
	//* * *
	return menu;
}