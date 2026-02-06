// ProvaDLLs.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "MagicDLL.h"
int main()
{
    int formatSize = 0;
    cout << "Benvinguts al creador de decks de MTG ºtm a C++!!\n";

    cout << "Format? (1= Standard - 60 cartes, 2= Commander - 100 cartes): ";
    int op;
    cin >> op;

    if (op == 1) formatSize = 60;
    else if (op == 2) formatSize = 100;
    else { cout << "Format invalid.\n"; return 0; }

    Deck* deck = CreateDeck();

    while (deck->GetCount() < formatSize) {
        Card c;

        cin.ignore(numeric_limits<streamsize>::max(), '\n'); // netejar buffer

        cout << "Nom de la carta: ";
        getline(cin, c.name);

        cout << "Tipus de la carta: ";
        getline(cin, c.type);

        cout << "Cost de mana: ";
        cin >> c.manaCost;

        deck->AddCard(c);

        cout << "Afegida al deck. Cartes afegides: "
            << deck->GetCount() << "/" << formatSize << "\n\n";
    }

    cout << "Deck completat.\n";
    vector<Card> totes = deck->GetAllCards();
    cout << "\nLlistat de cartes:\n";
    for (const auto& c : totes) {
        cout << "- " << c.name << " | " << c.type << " | Mana: " << c.manaCost << "\n";
    }

    DeleteDeck(deck);
    return 0;
}

