import React from 'react'
import Header from '../../components/Layout/Header'
import CartComponent from '../../components/Cart'

const titleHeader = {
    mainTitle: 'SHOP CART',
    extraTitle: 'Shop Cart'
}
const Cart = () => {
    return (
        <> <Header titleHeader={titleHeader} />
            <CartComponent />
        </>
    )
}

export default Cart