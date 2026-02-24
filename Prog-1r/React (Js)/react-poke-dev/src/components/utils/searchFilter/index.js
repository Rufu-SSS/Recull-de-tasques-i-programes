import React, { useState } from 'react'
import './styles.scss'
import { ALL_TYPES, TYPE_ICONS } from './filterConstants'

export const SearchFilter = ({ onNameChange, onTypesChange, types = [] }) => {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedTypes, setSelectedTypes] = useState([])

  const updateTextSearch = (e) => {
    const value = e.target.value.toLowerCase()
    setSearchTerm(value)
    onNameChange(value)
  }

  const toggleType = (type) => {
    const updatedTypes = selectedTypes.includes(type)
      ? selectedTypes.filter((t) => t !== type)
      : [...selectedTypes, type]

    setSelectedTypes(updatedTypes)
    onTypesChange(updatedTypes)
  }

  return (
    <div className="searchFilter">
      <input
        className="input-bar"
        placeholder="Search by name"
        type="text"
        value={searchTerm}
        onChange={updateTextSearch}
      />

      {types.length > 0 && (
        <div className="types-wrapper">
          {ALL_TYPES.filter((type) => types.includes(type)).map((type) => (
            <button
              key={type}
              className={`type-button ${selectedTypes.includes(type) ? 'active' : ''}`}
              onClick={() => toggleType(type)}
            >
              <img src={TYPE_ICONS[type]} alt={type} />
              {type}
            </button>
          ))}
        </div>
      )}
    </div>
  )
}
