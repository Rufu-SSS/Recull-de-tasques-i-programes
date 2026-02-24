import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import { getItems } from '../../../utils/pokemonAPI'
import { Nav } from '../../utils/nav'
import { SearchFilter } from '../../utils/searchFilter'

import logo from '../../../assets/pokemon-logo.png'

import './styles.scss'

export const ItemList = () => {
  const [items, setItems] = useState([])
  const [filter, setFilter] = useState('')
  const navigate = useNavigate()

  async function getData() {
    const data = await getItems(100, 0)
    setItems(data.results)
  }

  useEffect(() => {
    getData()
  }, [])

  return (
    <div>
      {items.length === 0 ? (
        <div>Loading Items</div>
      ) : (
        <div>
          <img
            src={logo}
            className="pokemon-logo"
            onClick={() => navigate('/pokemon')}
            alt="Pokemon Logo"
          />
          <Nav />
          <SearchFilter onChange={setFilter} />
          <div className="item-wrapper">
            {items
              .filter((item) => filter === '' || item.name.includes(filter))
              .map((item) => {
                const img_url =
                  'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/' +
                  item.name +
                  '.png'
                const link_path = '/item/' + item.name

                return (
                  <div
                    key={item.name}
                    className="item-card"
                    onClick={() => navigate(link_path)}
                  >
                    <img src={img_url} alt={item.name} />
                    <div className="item-name">{item.name}</div>
                  </div>
                )
              })}
          </div>
        </div>
      )}
    </div>
  )
}
