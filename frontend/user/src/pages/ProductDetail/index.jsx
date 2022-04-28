import React from 'react'
import Header from '../../components/Layout/Header'
import Description from '../../components/ProductDetail/Detail/Description'
import ImageProduct from '../../components/ProductDetail/Detail/Image'
import Option from '../../components/ProductDetail/Detail/Option'
import Recommend from '../../components/ProductDetail/Recommend'

const titleHeader = {
    mainTitle: 'SHOP DETAILP',
    extraTitle: 'Shop Detail'
}


const ProductDetail = () => {
    return (
        <>
            <Header titleHeader={titleHeader} />
            <div className="container-fluid py-5">
                <div className="row px-xl-5">
                    <ImageProduct />
                    <Option />
                </div>
                <Description />
            </div>
            <Recommend />
        </>
    )
}

export default ProductDetail