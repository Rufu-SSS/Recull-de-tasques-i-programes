#include <iostream>
#include <string>
using namespace std;

int main()
{
    bool playAgain = true;
    while (playAgain) {
        cout << "Bon dia usuari, introdueix una paraula en anglès i tot seguit et dic si es facil de pronunciar o no.\n";
        cout << "Inputs importants: 1 (para el programa)" << endl;

        string input;
        int consonantsSeguides = 0;
        bool dificil = false;

        cin >> input;

        if (input == "1") {
            playAgain = false;
            break;
        }

        for (char lletra : input) {
            lletra = tolower(lletra);

            if (lletra != 'a' && lletra != 'e' && lletra != 'i' && lletra != 'o' && lletra != 'u') {
                consonantsSeguides++;
                if (consonantsSeguides >= 4) {
                    dificil = true;
                    break;
                }
            }
            else {
                consonantsSeguides = 0;
            }
        }

        if (dificil) {
            cout << "No, no és fàcil de pronunciar." << endl;
        }
        else {
            cout << "Sí, és fàcil de pronunciar." << endl;
        }
    }

    return 0;
}
