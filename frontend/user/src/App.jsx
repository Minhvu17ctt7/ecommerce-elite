import { useState } from 'react'
import logo from './logo.svg'
import './App.css'
import Home from './pages/Home'
import Footer from './components/Layout/Footer'
import Topbar from './components/Layout/Topbar'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Shop from './pages/shop'
import ProductDetail from './pages/ProductDetail'
import Login from './pages/Login'

function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Topbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path='/shop' element={<Shop />} />
          <Route path='/product' element={<ProductDetail />} />
          <Route path='/login' element={<Login />} />
        </Routes>
        <Footer />
      </div>
    </BrowserRouter>
  )
}

export default App
