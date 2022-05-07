import { useState } from 'react'
import logo from './logo.svg'
import './App.css'
import Home from './pages/Home'
import Footer from './components/Layout/Footer'
import Topbar from './components/Layout/Topbar'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import Shop from './pages/shop'
import ProductDetail from './pages/ProductDetail'
import Login from './pages/Login'
import NotFound from './pages/NotFound'
import Register from './pages/Register'
import ToastComponent from './components/Toast'

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <ToastComponent />
        <Topbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path='/shop/:page' element={<Shop />} />
          <Route path='/products/:id/:pageReview' element={<ProductDetail />} />
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='*' element={<NotFound />} />
        </Routes>
        <Footer />
      </div>
    </BrowserRouter>
  )
}

export default App
