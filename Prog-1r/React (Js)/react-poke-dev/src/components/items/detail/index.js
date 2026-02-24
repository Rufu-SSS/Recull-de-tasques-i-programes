import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { getItem } from '../../../utils/pokemonAPI'
import styled from 'styled-components'

const ItemCard = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;

  background: #fff;
  border: 1px solid grey;
  padding: 30px 0px;
  max-width: 300px;

  margin-right: auto;
  margin-left: auto;

  font-family: Arial;
  font-weight: 600;
  text-transform: capitalize;
`

const ItemName = styled.div`
  text-align: center;
  margin-top: 25px;
  color: slategrey;
`

const ItemStat = styled.div`
  color: slategrey;
  font-weight: 400;
  padding: 10px 0 0 25px;
`

function ItemDetail() {
  const [item, setItem] = useState(null)
  const { name } = useParams()

  async function getData() {
    let data = await getItem(name)
    setItem(data)
  }

  useEffect(() => {
    if (typeof name === 'string' && name !== '') {
      getData()
    }
  }, [])

  const img_url = item
    ? 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/' +
      item.name +
      '.png'
    : ''

  const shortEffect =
    item?.effect_entries?.find((e) => e.language.name === 'en')?.short_effect ??
    'No description available'

  return (
    <div>
      {!item ? (
        <div>Loading items</div>
      ) : (
        <div>
          <ItemCard>
            <img
              align="center"
              style={{ display: 'block', margin: 'auto' }}
              src={img_url}
            />

            <ItemName>{item.name}</ItemName>

            <ItemStat>
              <strong>ID: </strong>
              {item.id}
            </ItemStat>
            <ItemStat>
              <strong>Cost: </strong>
              {item.cost}
            </ItemStat>
            <ItemStat>
              <strong>Category: </strong>
              {item.category.name}
            </ItemStat>
            <ItemStat>
              <strong>Effect: </strong>
              {shortEffect}
            </ItemStat>
          </ItemCard>
        </div>
      )}
    </div>
  )
}

export default ItemDetail
