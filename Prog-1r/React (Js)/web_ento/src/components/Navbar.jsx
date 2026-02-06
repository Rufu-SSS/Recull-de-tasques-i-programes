import { Link } from 'react-router-dom';

const Navbar = () => (
  <nav className="navbar">
    <h1>Elden Ring</h1>
    <div>
      <Link to="/">Inici</Link> | <Link to="/bosses">Bosses</Link> | <Link to="/map">Mapa</Link> | <Link to="/purchase?id=1">Compra</Link>
    </div>
  </nav>
);

export default Navbar;
