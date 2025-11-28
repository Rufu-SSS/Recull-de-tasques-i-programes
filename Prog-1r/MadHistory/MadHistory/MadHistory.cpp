#include <iostream>
#include <string>
using namespace std;

int main() {
    bool playAgain = true;

    while (playAgain) {
        string name;
        int age;
        float gold;
        char weaponType;
        bool hasCompanion;
        double damage;
        string place;

        cout << "Enter your hero name: ";
        getline(cin, name);

        cout << "Enter your age: ";
        cin >> age;
        cin.ignore(); 

        cout << "Enter a place you want to explore: ";
        getline(cin, place);

        cout << "How much gold do you have? ";
        cin >> gold;
        cin.ignore();

        cout << "Choose a weapon type (S = sword, B = bow): ";
        cin >> weaponType;
        cin.ignore();

        cout << "Do you have a companion? (1 = yes, 0 = no): ";
        cin >> hasCompanion;
        cin.ignore();

        damage = (gold / 2) + (age * 0.5);

        cout << "\n=== Your Adventure Begins ===\n";
        cout << "Brave " << name << ", " << age << " years old, enters the mysterious land of " << place << ".\n";

        if (weaponType == 'S' || weaponType == 's') {
            cout << "You wield a shiny sword with " << damage << " damage power.\n";
        }
        else if (weaponType == 'B' || weaponType == 'b') {
            cout << "You carry a long bow with " << damage << " damage power.\n";
        }
        else {
            cout << "You bring no proper weapon, but your courage shines bright.\n";
        }

        if (hasCompanion && gold > 50) {
            cout << "With your loyal companion and plenty of gold, you easily defeat the guardian!\n";
        }
        else if (hasCompanion || gold > 50) {
            cout << "You face the guardian with some advantage.\n";
        }
        else {
            cout << "Alone and poor, you must fight with wit.\n";
        }

        cout << "After a long journey, your story becomes legend in " << place << ".\n";

        string answer;
        cout << "\nDo you want to create a new adventure? (yes/no): ";
        getline(cin, answer);

        if (answer != "yes" && answer != "Yes") {
            playAgain = false;
        }

        cout << "\n";
    }

    cout << "Thanks for playing! Goodbye.\n";
    return 0;
}
