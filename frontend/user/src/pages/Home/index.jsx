import React from 'react'
import Categories from '../../components/Home/Categories'
import Featured from '../../components/Home/Featured'
import Navbar from '../../components/Home/Navbar'
import Product from '../../components/Home/Product'
import Vendor from '../../components/Home/Vendor'

const Home = () => {
    return (
        <>
            <Navbar />
            <Featured />
            <Categories />
            <Product />
            <Vendor />
        </>
    )
}

export default Home