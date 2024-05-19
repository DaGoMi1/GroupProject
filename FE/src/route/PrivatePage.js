import React from 'react'
import { Navigate } from 'react-router-dom'

const PrivatePage = ({user, children}) => {
  return (
    user ? children : <Navigate to='/loginPage'/>
  )
}

export default PrivatePage