import React from 'react';
import './SearchBar.css';

const SearchBar = ({ searchTerm, onSearchChange }) => {
  return (
    <div className="search-container">
      <div className="search-box">
        <svg 
          className="search-icon" 
          xmlns="http://www.w3.org/2000/svg" 
          viewBox="0 0 24 24" 
          fill="none" 
          stroke="currentColor" 
          strokeWidth="2"
        >
          <circle cx="11" cy="11" r="8"></circle>
          <path d="m21 21-4.35-4.35"></path>
        </svg>
        <input
          type="text"
          className="search-input"
          placeholder="Cerca Pokémon per nom..."
          value={searchTerm}
          onChange={(e) => onSearchChange(e.target.value)}
        />
        {searchTerm && (
          <button 
            className="clear-button"
            onClick={() => onSearchChange('')}
            aria-label="Netejar cerca"
          >
            ✕
          </button>
        )}
      </div>
      {searchTerm && (
        <p className="search-info">
          Cercant: <strong>{searchTerm}</strong>
        </p>
      )}
    </div>
  );
};

export default SearchBar;