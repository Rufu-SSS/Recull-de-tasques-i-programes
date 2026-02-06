import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import Bosses from './pages/Bosses';
import Map from './pages/Map';
import Purchase from './pages/Purchase';

const App = () => (
  <>
    <Navbar />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/bosses" element={<Bosses />} />
      <Route path="/map" element={<Map />} />
      <Route path="/purchase" element={<Purchase />} />
    </Routes>
  </>
);

export default App;
