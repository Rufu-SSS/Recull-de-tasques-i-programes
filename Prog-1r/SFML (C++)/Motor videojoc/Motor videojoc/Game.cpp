#include "Game.h"

Game::Game() :
    window(VideoMode({ 800, 600 }), "Joc de Plataformes Simple"),
    player(100, 100), player2(200,100),
    font("KOMIKAX_.ttf"),
    instructionsText(font) {
    window.setFramerateLimit(60);
    if (levelLoader.loadFromFile("level1.txt", window)) {
        player.setPosition(levelLoader.getPlayerStartPosition().x, levelLoader.getPlayerStartPosition().y);
    }
    instructionsText.setFont(font);
    instructionsText.setString("Thomas (VERD): WASD \nArribeu tots dos a la meta groga!\nNivell carregat des de: level1.txt");
    instructionsText.setCharacterSize(16);
    instructionsText.setFillColor(Color::Black);
    instructionsText.setPosition({ 10, 10 });
}


void Game::run() {
    while (window.isOpen()) {
        float dt = clock.restart().asSeconds();
        handleEvents();
        update(dt);
        draw();
    }
}

void Game::handleEvents() {
    // GESTIÓ D’ESDEVENIMENTS
    while (const std::optional event = window.pollEvent()) {
        if (event->is<Event::Closed>()) window.close();
        if (const auto* key = event->getIf<Event::KeyPressed>()) {
            if (key->scancode == Keyboard::Scancode::Escape) {
                window.close();
            }
        }
        // Cada personatge gestiona el seu input (polimorfisme!)
        player.handleInput();
        player2.handleInput();
    }
}

void Game::update(float dt) {


    // Actualitza personatges
    player.update(dt);
    levelLoader.update(dt);
    checkCollisions(player);
    checkGoal(levelLoader.getGoal());

}


void Game::draw() {
    window.clear(Color::White); // Cel blau clar
    // - Plataformes
    levelLoader.draw(window);

    // Dibuixar el jugador
    player.draw(window);
    // Aquí podries dibuixar més elements:
    
    // - Enemics
    // - UI (puntuació, vides, etc.)
        window.draw(instructionsText);

    window.display();
}

void Game::checkCollisions(Character& character) {
    FloatRect charBounds = character.getBounds();
    vector <Platform> ConjuntPlatform;
    ConjuntPlatform = levelLoader.getPlatforms();

    for (auto& platform : ConjuntPlatform) {
        FloatRect platformBounds = platform.getBounds();

        if (const std::optional intersection = (charBounds.findIntersection(platformBounds))) {
            // Col·lisió des de dalt
            if (charBounds.position.y < platformBounds.position.y &&
                charBounds.position.y + charBounds.size.y < platformBounds.position.y + 15) {
                character.stopFalling(platformBounds.position.y - charBounds.size.y);
            }
        }
    }
}

void Game::checkGoal(Platform goal) {
    if (player.getBounds().findIntersection(goal.getBounds())) {
        std::cout << "Felicitats! Tots dos heu arribat a la meta!" << std::endl;
        // Reinicia posicions
        player.setPosition(100, 100);
    }
}
