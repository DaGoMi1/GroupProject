import React from 'react'
import Navbar from '../components/Navbar'
import Carousel from '../components/Carousel'
import MainBoard from '../components/MainBoard'

const MainPage = ({setIsLogin}) => {

  return (
    <>
      <Navbar setIsLogin={setIsLogin}/>
      <Carousel/>
      <MainBoard/>
    </>
  )
}

export default MainPage