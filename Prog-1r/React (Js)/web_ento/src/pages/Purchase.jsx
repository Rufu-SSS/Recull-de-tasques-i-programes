import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import vorheesData from './vorhees.js'; // Assegura't que aquest fitxer exporta un array de jocs
import "./purchase.css";
const GameDetails = () => {
  const [searchParams] = useSearchParams();
  const [game, setGame] = useState(null);
  const gameId = searchParams.get('id');

  useEffect(() => {
    const selectedGame = vorheesData.find(g => g.id === gameId);
    setGame(selectedGame);
  }, [gameId]);

  const renderProductes = () => {
    if (!game?.productes || game.productes.length === 0) {
      return <p style={{ textAlign: 'center' }}>Aquest joc no té ítems disponibles.</p>;
    }

    const grouped = {};
    game.productes.forEach(item => {
      if (!grouped[item.categoria]) grouped[item.categoria] = [];
      grouped[item.categoria].push(item);
    });

    return Object.entries(grouped).map(([categoria, items]) => (
      <div key={categoria} className="carousel-container">
        <div className="carousel-title">{categoria}</div>
        <div className="carousel">
          {items.map((item, index) => (
            <div key={index} className="card">
              <img src={item.imatge} alt={item.nom} />
              <div className="title">{item.nom}</div>
              <div className="description">{item.descripcio}</div>
            </div>
          ))}
        </div>
      </div>
    ));
  };

  if (!game) {
    return (
      <div className="content">
        <h1>Detalls del Joc</h1>
        <p>No s'ha trobat cap joc amb aquest ID.</p>
      </div>
    );
  }

  return (
    <div>
      <h1 id="game-title">{game.titol}</h1>
      <div className="content" id="game-info">
        <img src={game.imatge} alt={game.titol} className="product-img" />
        <div className="title">{game.titol}</div>
        <div className="items">{game.items}</div>
        <div className="description">{game.descripcio}</div>
        <a href="http://localhost:3000/" className="button">← Tornar al Directori</a>
      </div>

      <h2 style={{ textAlign: 'center' }}>Ítems disponibles</h2>
      <div id="categories-container">
        {renderProductes()}
      </div>
    </div>
  );
};

export default GameDetails;
