#include <iostream>
#include <SFML/Graphics.hpp>
using namespace sf;
using namespace std;

int main()
{
    RenderWindow window(VideoMode({ 1000, 700 }), "Ping Pong Retro Challenge");

    //--------------------------
    // FONT I TEXT (Solució simple sense optional)
    //--------------------------
    Font font;
    // Prova diferents rutes fins trobar la font
    if (!font.openFromFile("arial.ttf") &&
        !font.openFromFile("C:/Windows/Fonts/arial.ttf") &&
        !font.openFromFile("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf")) {
        cerr << "Error: No es pot carregar cap font!" << endl;
        return -1;
    }

    // Text per Player 1 (esquerra superior)
    Text scoreText1(font, "0", 50);
    scoreText1.setFillColor(Color::White);
    scoreText1.setPosition({ 200.f, 30.f });

    // Text per Player 2 (dreta superior)
    Text scoreText2(font, "0", 50);
    scoreText2.setFillColor(Color::White);
    scoreText2.setPosition({ 750.f, 30.f });

    //--------------------------
    // OBJECTES
    //--------------------------

    // Player_1
    RectangleShape player1(Vector2f(10.f, 150.f));
    player1.setFillColor(Color::White);
    player1.setPosition({ 10.f, 275.f });

    // Player_2
    RectangleShape player2(Vector2f(10.f, 150.f));
    player2.setFillColor(Color::White);
    player2.setPosition({ 980.f, 275.f });

    // Pilota
    CircleShape ball(20.f);
    ball.setFillColor(Color::White);
    ball.setPosition({ 500.f, 350.f });

    // Velocitat de la pilota
    float ballVelocityX = 0.05f;
    float ballVelocityY = 0.05f;

    // Velocitat de la raqueta
    float playerSpeed = 0.1f;

    // Puntuació
    int score1 = 0;
    int score2 = 0;

    //--------------------------
    // BUCLE PRINCIPAL DEL JOC
    //--------------------------
    while (window.isOpen())
    {
        while (const optional event = window.pollEvent())
        {
            if (event->is<Event::Closed>())
                window.close();
        }

        //--------------------------
        // CONTROLS
        //--------------------------
        if (Keyboard::isKeyPressed(Keyboard::Key::Escape))
            window.close();

        // Moviment del Player_1
        if (Keyboard::isKeyPressed(Keyboard::Key::W) && player1.getPosition().y > 0)
            player1.move({ 0.f, -playerSpeed });
        if (Keyboard::isKeyPressed(Keyboard::Key::S) && player1.getPosition().y + player1.getSize().y < 700)
            player1.move({ 0.f, playerSpeed });

        // Moviment del Player_2
        if (Keyboard::isKeyPressed(Keyboard::Key::Up) && player2.getPosition().y > 0)
            player2.move({ 0.f, -playerSpeed });
        if (Keyboard::isKeyPressed(Keyboard::Key::Down) && player2.getPosition().y + player2.getSize().y < 700)
            player2.move({ 0.f, playerSpeed });

        // Moviment de la pilota
        ball.move({ ballVelocityX, ballVelocityY });

        //--------------------------
        // Rebot de la pilota
        //--------------------------
        // Rebot de la pilota a les parets superior i inferior
        if (ball.getPosition().y <= 0 || ball.getPosition().y + ball.getRadius() * 2 >= 700)
            ballVelocityY = -ballVelocityY;

        // Rebot de la pilota al Player_1
        if (player1.getGlobalBounds().findIntersection(ball.getGlobalBounds()))
        {
            ballVelocityX = -ballVelocityX;
        }

        // Rebot de la pilota al Player_2
        if (player2.getGlobalBounds().findIntersection(ball.getGlobalBounds()))
        {
            ballVelocityX = -ballVelocityX;
        }

        //--------------------------
        // PUNTUACIÓ
        //--------------------------
        // Suma puntuació al Player_1
        if (ball.getPosition().x + ball.getRadius() * 2 >= 1000) {
            score1 += 1;
            ball.setPosition({ 500.f, 350.f });
        }
        // Suma puntuació al Player_2
        if (ball.getPosition().x + ball.getRadius() * 2 <= 0) {
            score2 += 1;
            ball.setPosition({ 500.f, 350.f });
        }

        // Actualitzar el text de la puntuació
        scoreText1.setString(to_string(score1));
        scoreText2.setString(to_string(score2));

        if (score1 >= 5) {
            window.close();
            cout << "Ha guanyat el Player 1" << endl;
        }
        else if (score2 >= 5) {
            window.close();
            cout << "Ha guanyat el Player 2" << endl;
        }

        //--------------------------
        // WINDOW DRAW
        //--------------------------
        window.clear(Color::Black);
        window.draw(player1);
        window.draw(player2);
        window.draw(ball);
        window.draw(scoreText1);
        window.draw(scoreText2);
        window.display();
    }
}