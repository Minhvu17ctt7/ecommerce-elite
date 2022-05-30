import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import Footer from './components/Layout/Footer'
import Topbar from './components/Layout/Topbar'
import Cart from './pages/Cart'
import Checkout from './pages/Checkout'
import Home from './pages/Home'
import Login from './pages/Login'
import NotFound from './pages/NotFound'
import Order from './pages/Order'
import ProductDetail from './pages/ProductDetail'
import Register from './pages/Register'
import Shop from './pages/shop'
import UserDetail from './pages/UserDetail'
import PrivateRoute from './PrivateRoute'

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Topbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path='/shop' element={<Shop />} />
          <Route path='/products/:id' element={<ProductDetail />} />
          <Route path='/login' element={<Login />} />
          <Route path='/cart' element={<Cart />} />
          <Route path='/checkout' element={
            <PrivateRoute>
              <Checkout />
            </PrivateRoute>
          } />
          <Route path='/user-detail' element={
            <PrivateRoute>
              <UserDetail />
            </PrivateRoute>
          } />
          <Route path='/orders' element={
            <PrivateRoute>
              <Order />
            </PrivateRoute>
          } />
          <Route path='/register' element={<Register />} />
          <Route path='*' element={<NotFound />} />
        </Routes>
        <Footer />
      </div>
    </BrowserRouter>
  )
}

export default App
