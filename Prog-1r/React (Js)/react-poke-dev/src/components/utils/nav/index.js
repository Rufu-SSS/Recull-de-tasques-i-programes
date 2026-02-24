import React from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import './styles.scss'

import master from '../../../assets/masterball.png'
import pokeball from '../../../assets/pokeball.png'
import berry from '../../../assets/berry.png'
import rareCandy from '../../../assets/rare-candy.png'

const LinkIcon = ({ link, icon }) => {
  const navigate = useNavigate()
  return (
    <img
      src={icon}
      className="link-icon"
      onClick={() => navigate(link)}
      alt=""
    />
  )
}

const NAV_ITEMS = [
  { path: '/pokemon', label: 'Generation 1', icon: pokeball },
  { path: '/favourites', label: 'Favourites', icon: master },
  { path: '/berries', label: 'Berries', icon: berry },
  { path: '/items', label: 'Items', icon: rareCandy }
]

export const Nav = () => {
  const navigate = useNavigate()
  const location = useLocation()

  return (
    <div>
      <div className="nav-wrapper">
        {NAV_ITEMS.map(({ path, label, icon }) => (
          <div key={path}>
            <LinkIcon link={path} icon={icon} />
            <span
              className={`page-link ${location.pathname === path ? 'active' : ''}`}
              onClick={() => navigate(path)}
            >
              {label}
            </span>
          </div>
        ))}
      </div>
    </div>
  )
}
