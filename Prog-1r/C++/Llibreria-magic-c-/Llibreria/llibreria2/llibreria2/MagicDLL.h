#pragma once

#ifdef MAGICDLL_EXPORTS
#define MAGICDLL_API __declspec(dllexport)
#else
#define MAGICDLL_API __declspec(dllimport)
#endif

#include <string>
#include <vector>
using namespace std;

struct Card {
    string name;
    string type;
    string manaCost;
};

class Deck {
private:
    vector<Card> cards;
public:
    MAGICDLL_API void AddCard(const Card& card);
    MAGICDLL_API Card DrawCard();
    MAGICDLL_API int GetCount() const;
    MAGICDLL_API void Shuffle();
    MAGICDLL_API vector<Card> GetAllCards() const;
};

// Funcions globals de la DLL
extern "C" {
    MAGICDLL_API Deck* CreateDeck();
    MAGICDLL_API void DeleteDeck(Deck* deck);
}
