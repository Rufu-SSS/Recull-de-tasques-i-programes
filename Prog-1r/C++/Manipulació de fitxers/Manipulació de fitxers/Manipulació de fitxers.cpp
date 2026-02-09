#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cmath> 

using namespace std;

// #-----------------------------------------------------------------------------------------------#
// BLOC DE PREPARACIÓ I ESTRUCTURES (setup) -- [QUE NECESSITO?]
// #-----------------------------------------------------------------------------------------------#
// Objectiu: definir els requisits i les plantilles bàsiques de les dades pel seu futur funcionament.
// Contingut: les llibreries (fstream, vector), la definició de l'estructura de les dades del fitxer
// (struct GIFHeaderData) i la definició de la funció main.
// #-----------------------------------------------------------------------------------------------#

#pragma pack(push, 1) 

struct GIFHeaderData {
    char signature[3];
    char version[3];
    unsigned short screenWidth;
    unsigned short screenHeight;
    unsigned char packedFields;
    unsigned char backgroundColorIndex;
    unsigned char pixelAspectRatio;
};

#pragma pack(pop) 

// #-----------------------------------------------------------------------------------------------#
// BLOC DE GESTIÓ D'ENTRADA (input handling) -- [COM LLEGEIXO EL FITXER ORIGINAL?]
// #-----------------------------------------------------------------------------------------------#
// Objectiu: obrir i verificar el fitxer original d'extensió .gif (si pot ser).
// Contingut: obrim un ifstream en mode binari i comprovem que el fitxer s'obri adequadament amb 
// (is_open()), tot seguit en determinem la mida total del fitxer.
// #-----------------------------------------------------------------------------------------------#

void processGifFile(const string& inputFilename, const string& outputFilename) {
    ifstream inFile(inputFilename, ios::in | ios::binary | ios::ate); 

    if (!inFile.is_open()) {
        cerr << "ERROR: No es pot obrir el fitxer d'entrada: " << inputFilename << endl;
        cerr << "Assegura't que es troba al DIRECTORI DE L'EXECUTABLE." << endl;
        return;
    }

    streampos fileSize = inFile.tellg();
    inFile.seekg(0, ios::beg); 

    // #-----------------------------------------------------------------------------------------------#
    // BLOC D'ANÀLISI I EXTRACCIÓ DE DADES -- [QUINES DADES BUSCO O ANALITZO?]
    // #-----------------------------------------------------------------------------------------------#
    // Objectiu: llegir el fitxer començant per la capçalera d'aquest.
    // Contingut: llegir els primers bytes com si fos una estructura de dades i buscar les dades que ens
    // interessen més, l'amplada, l'altura, la versió del fitxer i la quantitat de colors que conté.
    // #-----------------------------------------------------------------------------------------------#

    GIFHeaderData headerData;

    inFile.read(reinterpret_cast<char*>(&headerData), sizeof(headerData));

    if (inFile.fail()) {
        cerr << "ERROR: Lectura incompleta de la capcalera." << endl;
        inFile.close();
        return;
    }

    string signature(headerData.signature, 3);
    string version(headerData.version, 3);

    int gctSizeCode = headerData.packedFields & 0x07;
    int colorCount = (int)pow(2, gctSizeCode + 1);

    cout << "========================================" << endl;
    cout << "## INFORME DEL FITXER GIF: " << inputFilename << endl;
    cout << "========================================" << endl;
    cout << "* Versio del GIF: " << version << endl;
    cout << "* Amplada de la Imatge: " << headerData.screenWidth << " pixels" << endl;
    cout << "* Alt de la Imatge: " << headerData.screenHeight << " pixels" << endl;
    cout << "* Quantitat maxima de colors (GCT): " << colorCount << endl;
    cout << "----------------------------------------" << endl;

    cout << "Iniciant duplicacio del fitxer..." << endl;
    cout << "* Mida total del fitxer: " << fileSize << " bytes." << endl;

    // #-----------------------------------------------------------------------------------------------#
    // BLOC D'EMMAGATZEMATGE I BUFFER BINARI (core operation) -- [ON GUARDO LA CÒPIA EN BINARI?]
    // #-----------------------------------------------------------------------------------------------#
    // Objectiu: acabar de treballar el fitxer .gif i guardar-ne una còpia.
    // Contingut: la creació d'una matriu de bytes (vector<char) amb la mida total del fitxer per tal de
    // bolcar tot el seu contingut dins aquesta matriu que hem creat (que vindria a ser un buffer).
    // #-----------------------------------------------------------------------------------------------#

    vector<char> fileContent(fileSize);

    inFile.seekg(0, ios::beg);
    inFile.read(fileContent.data(), fileSize);
    inFile.close();

    if (inFile.fail() && !inFile.eof()) {
        cerr << "ERROR: Fallada en la lectura de contingut a la matriu." << endl;
        return;
    }

    // #-----------------------------------------------------------------------------------------------#
    // BLOC QUE GESTIONA LA SORTIDA I EL TANCAMENT (output i cleanup) -- [COM GENERO EL FITXER NOU?]
    // #-----------------------------------------------------------------------------------------------#
    // Objectiu: re-escriure les dades del fitxer original a la còpia, vindria a ser un copiar enganxar
    // amb totes les dades, bolcar un pot de dades a un altre, si se m'entén esclar.
    // Contingut: obrir offstream per la còpia i com hem dit a l'objectiu, hi bolquem tots els continguts
    // que hem guardat dins la matriu a la còpia. Tot seguit tanquem els dos fitxers amb (inFile.close())
    // i (outFile.Close()).
    // #-----------------------------------------------------------------------------------------------#

    ofstream outFile(outputFilename, ios::out | ios::binary | ios::trunc);

    if (!outFile.is_open()) {
        cerr << "ERROR: No es pot obrir el fitxer de sortida: " << outputFilename << endl;
        return;
    }

    outFile.write(fileContent.data(), fileSize);
    outFile.close();

    if (outFile.fail()) {
        cerr << "ERROR: Fallada en l'escriptura al fitxer de sortida." << endl;
        return;
    }

    cout << "Duplicacio completada amb exit!" << endl;
    cout << "* El nou fitxer s'ha creat com: " << outputFilename << endl;
    cout << "========================================" << endl;
}

int main() {
    const string inputGif = "../startrek.gif";
    const string outputGif = "startrek_copy.gif";

    processGifFile(inputGif, outputGif);

    return 0;
}