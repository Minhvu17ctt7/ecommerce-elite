import React from 'react'
import CheckoutComponent from '../../components/Checkout'
import Header from '../../components/Layout/Header'

const titleHeader = {
    mainTitle: 'CHECKOUT',
    extraTitle: 'Home - Checkout'
}

const Checkout = () => {
    
    return (
        <> <Header titleHeader={titleHeader} />
            <CheckoutComponent />
        </>
    )
}

export default Checkout