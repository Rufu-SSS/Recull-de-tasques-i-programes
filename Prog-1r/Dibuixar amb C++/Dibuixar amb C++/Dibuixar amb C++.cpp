#include <iostream>
using namespace std;

int files;
void Ex1();
void Ex2();
void Ex3();
void Ex4();
void Ex5();

int main(){
    cout << "Introdueix el nombre de files que vols: ";
    cin >> files;

    int menu;
    while (true) {
        cout << "\n---------MENU----------" << endl;
        cout << "1. Triangle ple" << endl;
        cout << "2. Escala" << endl;
        cout << "3. Triangle buit" << endl;
        cout << "4. Triangle sencer" << endl;
        cout << "5. Diamant" << endl;
        cout << "6. Sortir" << endl;
        cout << "Opcio: ";

        cin >> menu;
        switch (menu) {
        case 1:
            Ex1();
            break;
        case 2:
            Ex2();
            break;
        case 3:
            Ex3();
            break;
        case 4:
            Ex4();
            break;
        case 5:
            Ex5();
            break;
        case 6:
            return 0;
        default:
            cout << "Opció no vàlida..." << endl;
        }
    }
 }
    

void Ex1() {
    
    cout << "\nDibuix triangle ple ->\n";
    for (int i = 1; i <= files; ++i) {
        for (int j = 1; j <= i; ++j) {
            cout << "*";
        }
        cout << endl;
    }
}
void Ex2() {
    cout << "\nDibuix escala ->\n";
    for (int i = 1; i <= files; ++i) {
        for (int j = 1; j <= (files - i); ++j) {
            cout << " ";
        }
        for (int j = 1; j <= files; ++j) {
            cout << "*";
        }
        cout << endl;
    }
}
void Ex3() {
    cout << "\nDibuix triangle buit ->\n";
    for (int i = 1; i <= files; ++i) {
        if (i == 1 || i == files) {
            for (int j = 1; j <= i; ++j) {
                cout << "*";
            }
        }
        else {
            cout << "*";
            for (int j = 1; j < (i - 1); ++j) {
                cout << " ";
            }
            cout << "*";
        }
        cout << endl;
    }
}
void Ex4() {
    cout << "\nDibuix triangle sencer ->\n";
    for (int i = 1; i <= files; ++i) {
        for (int j = 1; j <= i; ++j) {
            cout << "*";
        }
        cout << endl;
    }
    for (int i = files-1; i >= 1; --i) {
        for (int j = 1; j <= i; ++j) {
            cout << "*";
        }
        cout << endl;
    }
}
void Ex5() {
    cout << "\nDibuix diamant ->\n";
    for (int i = 1; i <= files; ++i) {
        for (int j = 1; j <= (files - i); ++j) {
            cout << " ";
        }
        for (int j = 1; j <= (2 * i - 1); ++j) {
            cout << "*";
        }
        cout << endl;
    }

    for (int i = files - 1; i >= 1; --i) {
        for (int j = 1; j <= (files - i); ++j) {
            cout << " ";
        }
        for (int j = 1; j <= (2 * i - 1); ++j) {
            cout << "*";
        }
        cout << endl;
    }
}