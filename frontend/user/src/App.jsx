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
import NotFound from './pages/NotFound'
import Register from './pages/Register'

function App() {

  const headerAndFooterExclusionArray = [
    'login',
    'register',
  ];

  let splitPathName = location.pathname.split('/');

  return (
    <BrowserRouter>
      <div className="App">
        {headerAndFooterExclusionArray.indexOf(splitPathName[1]) < 0 && <Topbar />}

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path='/shop' element={<Shop />} />
          <Route path='/product' element={<ProductDetail />} />
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='*' element={<NotFound />} />
        </Routes>
        {headerAndFooterExclusionArray.indexOf(splitPathName[1]) < 0 && <Footer />}
      </div>
    </BrowserRouter>
  )
}

export default App
