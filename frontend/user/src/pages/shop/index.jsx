import React from 'react'
import Header from '../../components/Layout/Header'
import Product from '../../components/Shop/Product'
import Sidebar from '../../components/Shop/Sidebar'

const titleHeader = {
    mainTitle: 'OUR SHOP',
    extraTitle: 'SHOP'
}

const Shop = () => {
    return (
        <>
            <Header main titleHeader={titleHeader} />
            <div className="container-fluid pt-5">
                <div className="row px-xl-5">
                    <Sidebar />
                    <Product />
                </div>
            </div>
        </>


    )
}

export default Shop