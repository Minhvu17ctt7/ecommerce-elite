import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import categoryApi from '../../api/categoryApi'
import productApi from '../../api/productApi'
import Header from '../../components/Layout/Header'
import Product from '../../components/Shop/Product'
import Sidebar from '../../components/Shop/Sidebar'

const titleHeader = {
    mainTitle: 'OUR SHOP',
    extraTitle: 'SHOP'
}

const Shop = () => {
    const [products, setProducts] = useState();
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        (async () => {
            const categoriesResponse = await categoryApi.getAllcategory();
            const productsResponse = await productApi.getAllProduct();
            setProducts(productsResponse.data);
            setCategories(categoriesResponse.data);
        })()
    }, []);
    return (
        <>
            <Header main titleHeader={titleHeader} />
            <div className="container-fluid pt-5">
                <div className="row px-xl-5">
                    <Sidebar categories={categories} />
                    <Product productPagination={products} />
                </div>
            </div>
        </>

    )
}

export default Shop