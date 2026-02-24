import React from 'react'
import logo from '../../assets/ball-logo.png'
import './styles.scss'

export const Loader = () => (
  <div className="loader-wrapper">
    <img src={logo} className="loader-logo" alt="Loading Logo" />
    <div className="loader-text">LOADING POKEMONS...</div>
  </div>
)
