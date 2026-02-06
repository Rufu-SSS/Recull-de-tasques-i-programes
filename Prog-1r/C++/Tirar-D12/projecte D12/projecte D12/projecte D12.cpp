#include "daus.h"
//Importar llibreries i fitxers ----------------------------------------------------------------------------------//
// QUE BUSQUEM?
//  Busquem importar l'únic fitxer que necessitem, aquest conté totes les llibreries i fitxers externs 
//  que farem servir al llarg del programa.
//* * *

int main()
{
    //Creem, inicialitzem i generem les variables i funcions -----------------------------------------------------//
    // QUE BUSQUEM?
    //  Definir una variable que ens permeti interaccionar amb el menu mitjançant un cin (opcioMenu2),
    //  crear un vector que emmagatzemi les tirades quan aquestes siguin una o més (vector<int>tirades) i
    //  un generador de llavors aleatoris (srand(time(NULL))).
    //* * *
    int opcioMenu2;
    vector<int>tirades;
    srand(time(NULL));

    //Bucle principal dins la funció main ------------------------------------------------------------------------//
    // QUE BUSQUEM?
    //  Tenir un menú estructurat amb un bucle que ens deixi moure'ns per aquest (bàsicament un switch),
    //  després d'això preparar tot el que farem dins el bucle (tirar daus, sumes, restes, multiplicacions,
    //  divisions i netejar búffers).
    //* * *

    do {
        cout << "\n\nBenvingut al simulador del llançament de daus del casino New Vegas!\n\n";
        cout << "\\=/=/= MENU PRINCIPAL =/=/=/" << endl;
        cout << "(1). Tirar un dau de 12 cares .(1)" << endl;
        cout << "(2). Tirar N daus de 12 cares .(2)" << endl;
        cout << "(3). Sumar totes les tirades .(3)" << endl;
        cout << "(4). Restar totes les tirades .(4)" << endl;
        cout << "(5). Multiplicar totes les tirades .(5)" << endl;
        cout << "(6). Dividir totes les tirades .(6)" << endl;
        cout << "(7). Netejar búffer cin .(7)" << endl;
        cout << "(8). Sortir del programa .(8)" << endl;

        cout << "Escull una opcio: ";
        cin >> opcioMenu2;
        cout << "\n";

        // Menú principal ----------------------------------------------------------------------------------------//
        // QUE BUSQUEM?
        //   Donar a l'usuari vàries opcions per a escollir i que a partir d'aquestes es pugui moure pel bucle,
        //   tot mitjançant un cin amb la variable anterior (opcioMenu2).
        //* * *

        switch (opcioMenu2) {
        case 1: {
            int valor = tiraDaus();
            tirades.push_back(valor);
            cout << "Valor guardat: " << valor << "\n";
            break;
        }
              //Cas 1 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  Volem que en aquesta opció l'usuari pugui tirar un dar, tal i com diu al menú. El que fem aqui
              //  dins, és guardar el valor de la tirada (int valor=tiraDaus();) i el posem dins el vector de 
              //  tirades (tirades.push_back(valor)), acabat d'això diem a l'usuari quin valor hem introduit al 
              //  vector.
              //* * *

        case 2: {
            int resposta;
            cout << "Quants daus vols fer apareixer en la simulacio: ";
            cin >> resposta;
            for (int i = 0; i < resposta; i++) {
                int valor = tiraDaus();
                tirades.push_back(valor);
                cout << "\nValor guardat: " << valor << "\n";
                cout << "\n";
            }
            break;
        }
              //Cas 2 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  Donem a l'usuari la opció a tirar n daus, tants com vulgui, fins i tot milions si ell/a ho demana.
              //  El que es fa aqui és bàsicament definir una variable per emmagatzemar la quantitat de daus que es 
              //  volen tirar per tal de poder donar-li la informació necessària al bucle for de sota, així sabrà 
              //  internament quantes vegades ha de repetir els processos que té dins seu.
              //  Els continguts d'aquest són una variable (valor) que torna els valors de la funció (tiraDaus()) 
              //  en int, si no tenien aquesta definició com a variable, ara sí la tenen. Després s'afegeix al dins
              //  el vector tirades i es mostra a l'usuari quins valors s'hi han introduit.
              //* * *

        case 3: {
            int total = 0;
            for (int v : tirades) total += v;
            cout << "El resultat de la suma del total de nombres es: " << total << "\n";
            break;
        }
              //Cas 3 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  En aquest cas tant curt creem i inicialitzem una variable a 0 (total) per tal de transferir-li 
              //  els valors del vector sumats, o sigui, que per cada tirada amb un valor propi el sumem i li afegim 
              //  a la variable total d'abans amb el bucle for. Aquest funcionarà tantes vegades com valors tingui 
              //  el vector tirades.
              //* * *

        case 4: {
            if (tirades.empty()) { cout << "No s'han tirat els daus encara. \n"; break; }
            int resultat = tirades[0];
            for (int i = 1; i < tirades.size(); i++)
                resultat -= tirades[i];
            cout << "El resultat de la resta del total de nombres es: " << resultat << "\n";
            break;
        }
              //Cas 4 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  En el quart cas comprovem si el vector (tirades) es troba buit o ja té valors, en cas que no en 
              //  tingui, sortirem del cas 4, en el cas positiu que sí en tingui, passarem al pròxim pas que és 
              //  crear una variable que pugui llegir tot el vector (tirades[0]) des de la primera posició. Amb un
              //  bucle for buscarem tots els valors del vector (tirades) i els restarem, tot seguit mostrarem el 
              //  resultat amb un cout.
              //* * *

        case 5: {
            if (tirades.empty()) { cout << "No s'han tirat els daus encara. \n"; break; }
            int resultatmult = 1;
            for (int v : tirades) resultatmult *= v;
            cout << "El resultat de la multiplicacio del total de nombres es: " << resultatmult << "\n";
            break;
        }
              //Cas 5 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  En aquest nou cas busquem multiplicar tots els valors del vector (tirades) entre ells, per això
              //  abans de res revisem que el vector (tirades) estigui buit. Després de introduir valors al vector
              //  es multiplicaran tots els valors entre ells i després el resultat de les operacions es lligarà a 
              //  la variable que hem creat abans (resultatmult) i finalment la mostrarem per consola.
              //* * *

        case 6: {
            if (tirades.empty()) { cout << "No s'han tirat els daus encara. \n"; break; }
            float resultatdiv = tirades[0];
            for (int i = 1; i < tirades.size(); i++) {
                if (tirades[i] == 0) { cout << "No es pot dividir entre 0.\n"; break; }
                resultatdiv /= tirades[i];
            }
            cout << "El resultat de la divisio del total de nombres es: " << resultatdiv << "\n";
            break;
        }
              //Cas 6 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  Primer de tot revisem que el vector tirades no és buit, així podrem treballar-lo. Per això crearem
              //  una variable que començi a la posició 0 del vector, amb això el bucle funcionarà tantes vegades com
              //  valors tingui el vector (tirades). Després de fer les divisions continuades es mostrarà el valor del
              //  resultat. [Les divisions es fan del nombre 1 / nombre 2 -> resultat anterior / nombre 3 -> ...].
              //* * *

        case 7: {
            tirades.clear();
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "Buffer cin i tirades netejats! \n";
            break;
        }
              //Cas 7 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  Aquest cas serveix per netejar els valors introduits anteriorment dins el vector, dins els cin i
              //  serveix per treure els valors temporals introduits, etc...
              //* * *
        case 8: {
            cout << "Sortint del simulador...\n";
            break;
        }
              //Cas 8 -------------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  Només busquem sortir del bucle i tancar el programa.
              //* * *

        default:
            cout << "opcio incorrecta\n";

            //Cas default ---------------------------------------------------------------------------------------//
              // QUE BUSQUEM?
              //  Avisar a l'usuari que s'ha equivocat d'input, no n'ha posat un que es pugui llegir bé.
              //* * *

        }

    } while (opcioMenu2 != 8);
    return 0;

    //Acabat el bucle -------------------------------------------------------------------------------------------//
    // QUE BUSQUEM?
    //  Si es tria el cas anterior 8, després de sortir del bucle switch, que també surti del programa retornant 0. 
    //* * *
}

