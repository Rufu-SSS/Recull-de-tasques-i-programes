# 🎮 RETRO PONG

## 👥 Autors
- **DavidSelles**
- **Rufus**

---

## 📝 Descripció del joc

**Retro Pong** és una implementació clàssica del joc Pong desenvolupada en **C++ amb SFML**.  
Dos jugadors controlen dues pales verticals i competeixen per ser el primer a arribar a **5 punts**, retornant una pilota que rebota per la pantalla.

### Característiques
- 🎯 Joc 2D clàssic per a dos jugadors
- 🕹️ Controls per teclat
- 🔄 Rebots de la pilota amb parets i pales
- 🏆 Sistema de puntuació en pantalla
- 🏁 Final automàtic de la partida

---

## 🎮 Controls

**Jugador 1 (esquerra)**
- `W` → Amunt
- `S` → Avall

**Jugador 2 (dreta)**
- `↑` → Amunt
- `↓` → Avall

**Altres**
- `ESC` → Sortir del joc

---

## 🏗️ Disseny descendent del programa
```
┌───────────────────────────────┐
│ RETRO PONG (main)             │
└───────────────┬───────────────┘
                │
        ┌───────▼────────┐
        │ Inicialització │
        └───────┬────────┘
                │
 ┌──────────────▼────────────────┐
 │ Crear finestra SFML           │
 │ Carregar font                 │
 │ Crear textos de puntuació     │
 │ Crear pales i pilota          │
 │ Inicialitzar variables        │
 └──────────────┬────────────────┘
                │
        ┌───────▼────────┐
        │ Bucle principal│
        └───────┬────────┘
                │
 ┌──────────────▼────────────────┐
 │ Gestió d’esdeveniments        │
 │ Controls dels jugadors        │
 │ Moviment de la pilota         │
 │ Col·lisions                   │
 │ Sistema de puntuació          │
 │ Condició de victòria          │
 └──────────────┬────────────────┘
                │
        ┌───────▼────────┐
        │ Renderització  │
        └────────────────┘
```
---

## 📋 Repartiment de tasques

### **DavidSelles**
- Implementació del moviment de la pilota
- Sistema de col·lisions
- Controls dels jugadors
- Lògica de puntuació i victòria
- Proves i depuració

### **Rufus**
- Disseny visual bàsic
- Gestió de textos i fonts
- Gestió del repositori GitHub

### **Tasques conjuntes**
- Definició de mecàniques
- Proves de jugabilitat
- Documentació del projecte
- Revisió final del projecte

---

## 🛠️ Tecnologies utilitzades
- **Llenguatge**: C++
- **Biblioteca gràfica**: SFML 3.x
- **Compilador**: g++ / Visual Studio
- **Control de versions**: Git i GitHub

---

## 🚀 Com executar el projecte

1. Instal·la **SFML**
2. Copia el fitxer `arial.ttf` a la carpeta del projecte
3. Compila el codi
4. Executa el programa

---

## 📦 Requisits
- Windows / Linux / macOS
- SFML 3.x o superior
- Font TrueType (`arial.ttf` o equivalent)

---

## 🎓 Projecte acadèmic

Projecte desenvolupat com a pràctica de programació amb **C++ i SFML**.

**Data**: 16/12/2025

