# Tres en Ratlla

## Autors:
- **Pol Planas**  
- **Jordi Rubau**

## Descripció del joc:
Aquest joc tracta del **Tres en ratlla (Tic Tac Toe)**, on, igual que en el mític joc, l’objectiu és **encadenar tres fitxes en fila** abans que el teu contrincant.  

Les fitxes són:
- **Jugador 1:** X  
- **Jugador 2:** O  

El tauler és de **9 caselles**, i si s’omplen totes sense aconseguir concatenar tres fitxes, el joc acabarà **en empat**.  

Però no et preocupis, Sempre tindràs l’opció de **tornar a jugar**.  
Un cop acabeu totes les rondes que vulgueu, es mostrarà el **guanyador final** entre els dos contrincants amb el nombre total de partides guanyades.

---

*Esquema del disseny descendent:*

PROGRAMA: Tres en Ratlla  
│  
-- MAIN  
│   -- Demanar noms dels jugadors  
│   -- Crear objectes Alumne1 i Alumne2  
│   -- Crear objecte Joc  
│   -- Bucle principal de joc  
│   │   -- Cridar Joc.jugar()  
│   │   -- Preguntar si continuar  
│   │   -- Mostrar resultats finals  
│   -- Fi del programa  
│  
-- CLASSE JOC  
│   -- inicialitzarTauler()  
│   -- imprimirTauler()  
│   -- esMovimentValid()  
│   -- ferMoviment()  
│   -- comprovarGuanyador()  
│   -- taulaPlena()  
│   -- canviarTorn()  
│   -- reiniciarPartida()  
│   -- jugar()  
│        -- Mostrar tauler  
│        -- Demanar moviment jugador  
│        -- Fer moviment i comprovar guanyador  
│        -- Comprovar empat  
│        -- Canviar torn o acabar partida  
│  
-- CLASSE ALUMNE1  
│   -- getNomJug1()  
│   -- getSimbolJug1()  
│   -- getVictoriesJug1()  
│   -- incrementarVictoriesJug1()  
│   -- triarMovimentJug1()  
│  
-- CLASSE ALUMNE2  
       │-- getNomJug2()  
       │-- getSimbolJug2()  
       │-- getVictoriesJug2()  
       │-- incrementarVictoriesJug2()  
       │-- triarMovimentJug2()  

---

*Gaudeix del clàssic Tres en Ratlla amb una nova experiència!*
