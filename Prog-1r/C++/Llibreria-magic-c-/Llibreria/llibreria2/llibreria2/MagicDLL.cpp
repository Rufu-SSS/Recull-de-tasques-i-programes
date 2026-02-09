// MagicDLL.cpp
#include "MagicDLL.h"
#include <algorithm>
#include <random>
using namespace std;

void Deck::AddCard(const Card& card) {
    cards.push_back(card);
}

Card Deck::DrawCard() {
    if (cards.empty()) return { "", "", 0 };
    Card c = cards.back();
    cards.pop_back();
    return c;
}

int Deck::GetCount() const {
    return cards.size();
}

void Deck::Shuffle() {
    random_device rd;
    mt19937 g(rd());
    shuffle(cards.begin(), cards.end(), g);
}

// Funcions globals
Deck* CreateDeck() {
    return new Deck();
}

void DeleteDeck(Deck* deck) {
    delete deck;
}

vector<Card> Deck::GetAllCards() const {
    return cards;
}
