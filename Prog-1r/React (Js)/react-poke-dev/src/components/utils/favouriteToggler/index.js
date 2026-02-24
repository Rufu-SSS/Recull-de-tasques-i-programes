import { useState, useEffect } from 'react'
import './styles.scss'

import {
  isFavourite,
  addFavourite,
  removeFavourite
} from '../../../utils/favourites'

import fullHeart from '../../../assets/heart-full.png'
import emptyHeart from '../../../assets/heart-empty.png'

export const FavouriteToggler = ({ element }) => {
  const [icon, setIcon] = useState('')

  const favIcon = () => (isFavourite(element) ? fullHeart : emptyHeart)

  useEffect(() => {
    setIcon(favIcon())
  }, [])

  const toggleFav = (e) => {
    e.stopPropagation()
    e.preventDefault()

    isFavourite(element) ? removeFavourite(element) : addFavourite(element)
    setIcon(favIcon())
  }

  return (
    <img src={icon} className="favourite-toggler" onClick={toggleFav} alt="" />
  )
}
