// IntroduccioBucles.cpp : This file contains the 'main' function. Program execution begins and ends there. 
// 

#include <iostream> 
using namespace std;

int main()
{
    bool p = true;
    int w = 0;
    int q = 0;

    cout << "Counting forward: " << endl;
    for (int i = 0; i < 10; i++) {
    cout << i << " ";
    }

    cout << "\n\nCounting backward: " << endl;
    for (int o = 9; o > 0; o = o - 1) {
    cout << o << " ";
    }

    cout << "\n\nCounting by fives: " << endl;
    while (p == true) {
        cout << q << " ";
        q = q + 5;
        if (q > 50)
        {
        p = false;
        }
    }

    cout << "\n\nCounting with null statements: " << endl;
    while (p == true) {
        cout << w << " ";
        w = w + 1;
        if (w == 10) {
            p = NULL;
        }
    }



    cout << "\n\nCounting with nested for loops: " << endl;
    for (int x = 0; x < 5; x++) {
        for (int y = 0; y < 3; y++) {
            cout << x << "." << y;
            if (y < 2) {
                cout << " ";
            }
        }
        cout << endl;
    }
    return 0;
}

