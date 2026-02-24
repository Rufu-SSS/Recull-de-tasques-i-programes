const BASE_URL = 'https://pokeapi.co/api/v2'

const fetchAPI = async (endpoint) => {
  const res = await fetch(`${BASE_URL}${endpoint}`)
  if (!res.ok) {
    throw new Error(`API error: ${res.status} ${res.statusText}`)
  }
  return res.json()
}

export const getPokemons = (limit = 251, offset = 0) =>
  fetchAPI(`/pokemon?limit=${limit}&offset=${offset}`)

export const getPokemon = (name = '') => fetchAPI(`/pokemon/${name}`)

export const getBerries = (limit = 70) => fetchAPI(`/berry?limit=${limit}`)

export const getBerry = (name = '') => fetchAPI(`/berry/${name}`)

export const getItems = (limit = 120, offset = 0) =>
  fetchAPI(`/item?limit=${limit}&offset=${offset}`)

export const getItem = (name = '') => fetchAPI(`/item/${name}`)
