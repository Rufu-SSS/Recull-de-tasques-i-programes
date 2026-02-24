// App.jsx
import './index.css';
import { useState } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

import { PokemonList } from './components/pokemon/list';
import PokemonDetail from './components/pokemon/detail';
import { ItemList } from './components/items/list';
import ItemDetail from './components/items/detail';

function App() {
  const [darkMode, setDarkMode] = useState(false);

  return (
    // Classe directament al div principal que engloba tota l'app
    <div className={darkMode ? 'dark-theme' : 'light-theme'} style={{ minHeight: '100vh', padding: '30px' }}>
      
      {/* 🔘 Botó Dark Mode */}
      <div style={{ position: 'fixed', top: '10px', right: '10px', zIndex: 999 }}>
        <button
          onClick={() => setDarkMode(!darkMode)}
          style={{ padding: '10px 20px', fontSize: '16px' }}
        >
          {darkMode ? '☀️ Light' : '🌙 Dark'}
        </button>
      </div>

      {/* Contingut / Rutes */}
      <Routes>
        <Route path="/" element={<Navigate to="/pokemon" replace />} />
        <Route index path="pokemon" element={<PokemonList favs={false} />} />
        <Route index path="favourites" element={<PokemonList favs={true} />} />
        <Route index path="berry" />
        <Route index path="items" element={<ItemList />} />
        <Route path="/pokemon/:name" element={<PokemonDetail />} />
        <Route path="/item/:name" element={<ItemDetail />} />
        <Route path="*" element={<Navigate to="/pokemon" replace />} />
      </Routes>
      
    </div>
  );
}

export default App;