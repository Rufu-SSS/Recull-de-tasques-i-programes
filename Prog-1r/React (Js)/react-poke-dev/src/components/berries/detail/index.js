import React, { useState, useEffect } from 'react'
import styled from 'styled-components'
import { useNavigate, useParams } from 'react-router-dom'

import { getBerries } from '../../../utils/berriesAPI'

const BerryCard = styled.div`
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

const BerryName = styled.div`
  text-align: center;
  margin-top: 25px;
  color: slategrey;
`

const BerryStat = styled.div`
  color: slategrey;
  font-weight: 400;
  padding: 10px 0 0 25px;
`

const ReturnButton = styled.a`
  position: absolute;
  top: 0px;
  right: 10px;
  color: slategrey;
  font-size: 24px;
  cursor: pointer;
  padding: 10px;
`

function BerryDetail() {
  const [berry, setBerry] = useState(null)
  const navigate = useNavigate()
  const { name } = useParams()

  async function getData() {
    let data = await getBerries(name)
    setBerry(data)
  }

  useEffect(() => {
    if (typeof name === 'string' && name !== '') {
      getData()
    }
  }, [])

  const img_url =
    'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/' +
    name +
    '.gif'

  return (
    <div>
      {!berry ? (
        <div>Loading berries</div>
      ) : (
        <div>
          <BerryCard>
            <ReturnButton onClick={() => navigate('/berry')}>
              &times;
            </ReturnButton>

            <img
              align="center"
              style={{ display: 'block', margin: 'auto' }}
              src={img_url}
            />

            <BerryName>{berry.name}</BerryName>

            <BerryStat>
              <strong>ID: </strong>
              {berry.id}
            </BerryStat>
            <BerryStat>
              <strong>Name: </strong>
              {berry.name}
            </BerryStat>
            <BerryStat>
              <strong>Groth Rate: </strong>
              {berry.growth_time}
            </BerryStat>
            <BerryStat>
              <strong>Max harvest: </strong>
              {berry.max_harvest}
            </BerryStat>
            <BerryStat>
              <strong>Gift Power: </strong>
              {berry.natural_gift_power}
            </BerryStat>
            <BerryStat>
              <strong>Size: </strong>
              {berry.size}
            </BerryStat>
          </BerryCard>
        </div>
      )}
    </div>
  )
}

export default BerryDetail
