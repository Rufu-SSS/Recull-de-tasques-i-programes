#include "Platform.h"

Platform::Platform(float x, float y, float width, float height)
    : GameObject(x, y, width, height) {
    shape.setFillColor(Color(100, 100, 100));
}
Platform::Platform(float x, float y) : GameObject(x, y, 50, 50) {
    shape.setFillColor(Color::Yellow);
}

void Platform::update(float dt) {
    // Les plataformes no es mouen
}

PlatformMap::PlatformMap() : goal(150, 150) {
    playerStartPosition = Vector2f(100, 100);
}

Platform PlatformMap::getGoal()
{
    return goal;
}

// Carrega el mapa des d'un fitxer
bool PlatformMap::loadFromFile(const std::string& filename, RenderWindow& window) {
    platforms.clear();
    std::ifstream file(filename);

    if (!file.is_open()) {
        std::cout << "No s'ha pogut obrir el fitxer: " << filename << std::endl;
        return false;
    }

    std::string line;
    int y = 0;
    int xtotal = 0;
    std::cout << "Carregant mapa des de: " << filename << std::endl;

    while (std::getline(file, line)) {
        for (int x = 0; x < line.length(); x++) {
            char tile = line[x];
            xtotal = x * TILE_SIZE;
            if (tile == '1') {
                // Crea una plataforma
                platforms.push_back(Platform(
                    x * TILE_SIZE,
                    y * TILE_SIZE,
                    TILE_SIZE,
                    TILE_SIZE
                ));
            }
            else if (tile == 'P') {
                // Marca la posició inicial del jugador
                playerStartPosition.x = x * TILE_SIZE;
                playerStartPosition.y = y * TILE_SIZE;
                std::cout << "  Posició jugador: (" << x << ", " << y << ")" << std::endl;
            }
            else if (tile == 'G') {
                // Marca la posició de la meta
                goal.setPosition(x * TILE_SIZE, y * TILE_SIZE);
                std::cout << "  Posició meta: (" << x << ", " << y << ")" << std::endl;
            }
        }
        y++;
    }
    file.close();
    Vector2u Mides;
    Mides.x = xtotal;
    Mides.y = y * TILE_SIZE;
    window.create(VideoMode(Mides), "Joc de Plataformes Simple");

    std::cout << "Mapa carregat: " << platforms.size() << " plataformes" << std::endl;
    return true;
}

// Getters
const std::vector<Platform>& PlatformMap::getPlatforms() const {
    return platforms;
}

Vector2f PlatformMap::getPlayerStartPosition() const {
    return playerStartPosition;
}

// Dibuixa totes les plataformes
void PlatformMap::draw(RenderWindow& window) {
    for (auto& platform : platforms) {
        platform.draw(window);
    }
    goal.draw(window);

}

// Actualitza totes les plataformes (per si hi ha plataformes mòbils)
void PlatformMap::update(float dt) {
    for (auto& platform : platforms) {
        platform.update(dt);
    }
}
