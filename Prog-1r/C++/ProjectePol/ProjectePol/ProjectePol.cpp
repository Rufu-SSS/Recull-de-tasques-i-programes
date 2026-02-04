#include <iostream>
#include <string>
#include <cstdlib>
#include <Windows.h>
#include <algorithm>
using namespace std;
const int NUM_WORDS = 10;
//=============================================================================================================================================//
// INCLUDES & CONSTANT VARIABLES
//=============================================================================================================================================//
// In the following c++ code you'll find a variety of functions that complement each other while being used one next to the 
// other. The first one (startStory(...)) does the same as it's name says, it literally starts the story. The second function
// (saveWords(...))saves the inputs the user makes in the terminal, that happens because this function wants the user to input a serie of words
// that are needed in the context of the story. The last (printMyStory(...)) one does the same as the last one, does the same as it's name says, 
// this time it tells the whole story we made up on English class and adds the words the user submitted to the terminal in the 
// last function.
//=============================================================================================================================================//
// ATTENTION: THE STORY IS VERY WEIRD!!
//=============================================================================================================================================//

char startStory(const string& prompt) { //This function starts the story.
	char respostaa;
	while (true) {
		cout << prompt;
		cin >> respostaa;
		respostaa = toupper(respostaa);
		// We turn the user's input characters into their uppercase form.
		if (respostaa == 'Y' || respostaa == 'N') {
			break;
		}
		cout << "Thou should try again, for the entry is wrong\n";
		// If the input is different from 'Y' and 'N', we'll ask the user to try again.
	}
	return respostaa; //The function ends .
}



void saveWords(string words[NUM_WORDS]) {
	//This function will save and collect the words from the user.
	cout << "Fill the gaps on the following text with the words asked next:\n\n";

	const string prompts[NUM_WORDS] = {
	"#1: Chose a body part:",
	"#2: Chose a color ending with -ish",
	"#3: Chose a place",
	"#4: Chose a verb",
	"#5: Chese an adjective",
	"#6: Chose an object",
	"#7: Chose a noun",
	"#8: Chose another adjective",
	"#9: Chose a noun related to time",
	"#10: Chose a random name"
	};
	// This string of prompts will ask the user some questions that will affect the story

	for (int i = 0; i < NUM_WORDS; i++) {
		// Now we'll collect the inputs recieved from the user and save them into an array of 'i' words
		// where 'i' needs to reach the same value as the constant int NUM_WORDS, which is 10.
		cout << "Write a word #" << i + 1 << ": ";
		cin >> words[i];
	}
}

void printMyStory(const string words[NUM_WORDS]) {
	// Now that we have the words, we'll display the story completed and filled with the words submitted earlier by the user.
	cout << "\n--- crazy story ---\n";
	cout << "So, it was a dark night near Arrowstreet Inc, Boston’s streets were empty dry but not enough, there was a couple walking by holding *" << words[0] << "* gently.\n";
	Sleep(4000);
	cout << "When the man told her it was his time to leave, she smiled shyly and waved him saying goodbye. She fades 50 meters away in a " << words[1] << " fog.\n";
	Sleep(4000);
	cout << "The man walked through the city alone, he helped her girlfriend get home so he could do it too. Benjamin was a people of goodwill, he loved helping others and talking a lot but soon he would find out that maybe he shouldn’t have talked to that man. When he was arriving " << words[2] << ", \n";
	Sleep(6000);
	cout << "he saw someone by the door with his eyes staring deep at his soul, when he asked him to move, he simply did not answer and proceeded to " << words[3] << " him by the arms, trying to immobilize him. \n";
	Sleep(5000);
	cout << "Benjamin fought back but the man was stronger than him and ended up " << words[4] << endl;
	Sleep(6000);
	cout << "with just one strike to the nose. Days passed until Benjamin woke up, he had been sleeping in a small room alongside some sheep. It took him seven minutes to realize he was unable to move since he had " << words[5] << " on his arms and the sheep were slowly eating his robes away from him.\n";
	Sleep(6000);
	cout << "Later that day, someone made his way into the room and gave him some homemade cheese, a loaf of bread and a glass of water. He told him that he would be participating forcefully in a secret military " << words[6] << " that wouldn’t be possible to do in legal terms.\n";
	Sleep(6000);
	cout << "Benjamin was told to remain calm and follow the orders of not screaming, eating his meals three times a day and behaving like a sheep. He felt a shiver all over his spine, he knew something was wrong but couldn’t understand it, were those people really " << words[7] << " or they were just part of a cult?\n";
	Sleep(6000);
	cout << "Days passed and he started dissociating from himself, something was off, and he felt it, was he growing white hair all over his body?? What the fuck had he been eating all this time? This wicked individual showed off a " << words[8] << " later and told him that he would be set free near a place called" << words[9] 
		<< " Farm, he followed by saying that he must behave like a sheep for a year or else he would’ve been brought to a slaughtering house.Deep down in a spiral of dissociation and stress he completely lost his whole identity.\n";
	Sleep(1000);
}







int main() {
	//This is the main function, it calls and uses the previous functions in order to show in the terminal the weird story i wrote.
	if (startStory("Dost thou want to repeat the story again ? (Y/N):") == 'Y') {
		string words[NUM_WORDS];
		saveWords(words);
		printMyStory(words);
	}
	while (startStory("\n\nDost thou want to repeat the story again? (Y/N): ") == 'Y') {
		system("cls"); 
		// We clear the terminal in the case, the user wanted to repeat it again.
		main();
	}
	std::cout << "Shall we meet again some time..." << endl;
	return 0;
}