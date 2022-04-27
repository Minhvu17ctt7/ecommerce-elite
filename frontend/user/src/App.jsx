import { useState } from 'react'
import logo from './logo.svg'
import './App.css'
import Home from './pages/Home'
import Footer from './components/Footer'
import Header from './components/Header'

function App() {

  return (
    <div className="App">
      <Header />
      <Home />
      <Footer />
    </div>
  )
}

export default App
