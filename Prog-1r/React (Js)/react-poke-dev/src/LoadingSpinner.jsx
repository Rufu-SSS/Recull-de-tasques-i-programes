import React from 'react';
import './LoadingSpinner.css';

const LoadingSpinner = () => {
  return (
    <div className="loading-container">
      <div className="pokeball">
        <div className="pokeball-top"></div>
        <div className="pokeball-middle"></div>
        <div className="pokeball-bottom"></div>
        <div className="pokeball-button"></div>
      </div>
      <p className="loading-text">Capturant Pokémon...</p>
    </div>
  );
};

export default LoadingSpinner;